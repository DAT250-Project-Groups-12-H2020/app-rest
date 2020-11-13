'''
script to simulate a physical voting device, with buttons [green, red, send, rest]
that interact with the rest API
reads config from device_config.ini file
TODO: create a new user with a api call??
'''
import time
import requests
import configparser
import sys

config = configparser.ConfigParser()
config.read('device_config.ini')
firebase_api_key = config['TOKENS']['firebase_api_key']
auth_token = config['TOKENS']['auth_token']
base_url = config['URL']['base_url']
session = requests.Session()
first_votes, second_votes = 0, 0
cookies = None

def main():
    login()
    pollid = config['POLL']['pollid']
    print('showing votes on poll number:', pollid, '\n')
    while True:
        update_status()
        time.sleep(3)

def update_status():
    '''get the total poll votes, if changed since last update'''
    global first_votes, second_votes
    pollid = config['POLL']['pollid']
    url = f"{base_url}/api/v1/polls/?id={pollid}"
    payload = '{}'
    headers = {'Cookie': cookies,
         'Content-Type': 'application/json',
         }
    response = session.request("GET", url, headers=headers, data=payload)
    if response.ok:
        first_count, second_count = get_total_votes(response)
        if first_count != first_votes or second_count != second_votes:
            #votes changed
            first_votes = first_count
            second_votes = second_count
            print ("\033[A                             \033[A")
            print('total votes:', first_votes, second_votes)
    else:
        print('failed to update')

def get_total_votes(response):
    '''get total number of votes from json response'''
    votes = response.json()['votes']
    first_count, second_count = 0, 0
    for vote in votes:
        first_count += vote['firstVotes']
        second_count += vote['secondVotes']
    return first_count, second_count


def login():
    '''log in to app and save session cookie '''
    global cookies
    print('logging in...')
    url = f'{base_url}/api/v1/session/login'
    headers = {
        'Authorization': f'Bearer {auth_token}',
    }
    response = session.post(url, headers=headers, data={})
    if not response.ok:
        print('login failed',response.status_code, response.reason)
        sys.exit()
    else:
        session_token = response.headers.get('Set-Cookie').split(';')[0]
        cookies = f'authenticated=true; {session_token}'
        print('success')


main()
