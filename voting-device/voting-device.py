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
auth_token = config['TOKENS']['auth_token']
base_url = config['URL']['base_url']
session = requests.Session()
first_votes, second_votes = 0, 0
cookies = None

def main():
    global first_votes, second_votes
    login()
    me()
    pollid = config['POLL']['pollid']
    print('voting on poll id:', pollid)
    while True:
        char = input('insert 1 to vote first, 2 for vote second, s for send and r for reset:\n')
        if char not in ['1', '2', 's', 'r']:
            print('illegal input')
            continue
        if char == '1':
            first_votes += 1
        if char == '2':
            second_votes += 1
        if char == 'r':
            first_votes, second_votes = 0, 0
        if char == 's':
            send_votes()
        else:
            print(f'current votes: first {first_votes}, second {second_votes}')


def login():
    '''log in to app and save session cookie '''
    global cookies
    print('logging in...')
    url = f'{base_url}/api/v1/session/login'
    headers = {
        'Authorization': f'Bearer {auth_token}',
    }
    response = session.post(url, headers=headers, data={})
    session_token = response.headers.get('Set-Cookie').split(';')[0]
    cookies = f'authenticated=true; {session_token}'
    if not response.ok:
        print('login failed',response.status_code, response.reason)
    else:
        print('success')


def me():
    '''print user info'''
    url = f'{base_url}/api/v1/accounts/me'
    headers = {    'Cookie': cookies    }
    response = session.request("GET", url, headers=headers, data={})
    if not response.ok:
        print('user info failed:')
        print(response.status_code, response.reason)
    else:
        print('logged in as', response.json()['role'])


def send_votes():
    '''send votes to selected voteid and reset'''
    global first_votes, second_votes
    print('sending votes...')
    print(f'final count - firstVotes: {first_votes}, secondVotes: {second_votes}')
    pollid = config['POLL']['pollid']
    url = f"{base_url}/api/v1/polls/{pollid}/vote"
    payload = '{"firstVotes": ' + str(first_votes) + ',"secondVotes": ' + str(second_votes) + '}'
    headers = {'Cookie': cookies,
         'Content-Type': 'application/json',
         }
    response = session.request("POST", url, headers=headers, data=payload)
    if response.ok:
        print('votes succesfully sent')
        first_votes, second_votes = 0, 0
    else:
        print(response.json()['error'])
        print(response.json()['message'])


main()
