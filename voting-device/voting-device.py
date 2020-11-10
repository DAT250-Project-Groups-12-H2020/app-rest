'''
script to simulate a physical voting device, with buttons [green, red, send, rest]
that interact with the rest API
reads config from device_config.ini file
TODO: create a new user with a api call??
'''
import requests
import configparser

config = configparser.ConfigParser()
config.read('device_config.ini')
firebase_api_key = config['TOKENS']['firebase_api_key']
refresh_token = config['TOKENS']['refresh_token']
base_url = config['URL']['base_url']

first_votes, second_votes = 0,0

def main():
    global first_votes, second_votes
    login()
    me()
    while True:
        char = input('insert 1 to vote first, 2 for vote second, s for send and r for reset:\n')
        if char not in ['1','2','s','r']:
            print('illegal input')
            continue
        if char == '1':
            first_votes +=1
        if char == '2':
            second_votes +=1
        if char == 'r':
            first_votes, second_votes = 0,0
        if char == 's':
            send_votes()
        else:
            print(f'current votes: first { first_votes }, second {second_votes}')

def login():
    print('logging in...')
    url = f'{base_url}/api/v1/session/login'
    headers = {
    'Authorization': f'Bearer {auth_token}',
    }
    response = requests.request("POST", url, headers=headers, data = {})
    session_token = ...
    cookies = f'authenticated=true; session={session_token}'
    if not response.ok:
        print(response.status_code, response.reason)

def me():
    url = f'{base_url}/api/v1/accounts/me'
    headers = {
    'Cookie': cookies
    }
    response = requests.request("GET", url, headers=headers, data = {})
    if not response.ok:
        print('me failed:')
        print(response.status_code, response.reason)

def send_votes():
    '''send votes to selected voteid and reset'''
    global first_votes, second_votes
    print('sending votes...')
    pollid = config['POLL']['pollid']
    url = f"{base_url}/api/v1/polls/{pollid}/vote"
    payload = '{"firstVotes": '+str(first_votes)+',"secondVotes": '+str(second_votes)+'}'
    headers = {
    'Content-Type': 'application/json',
    'Cookie': cookies
    }
    response = requests.request("POST", url, headers=headers, data = payload)
    print(response.text.encode('utf8'))
    first_votes, second_votes = 0, 0

main()
