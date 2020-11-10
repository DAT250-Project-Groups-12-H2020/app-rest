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
session = request.Session()

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
    url = 'http://localhost:8090/api/v1/session/login'
    headers = {
    'Authorization': 'Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjBlM2FlZWUyYjVjMDhjMGMyODFhNGZmN2RjMmRmOGIyMzgyOGQ1YzYiLCJ0eXAiOiJKV1QifQ.eyJwcm92aWRlcl9pZCI6ImFub255bW91cyIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9kYXQyNTAtZ3ItMi1oMjAyMC1hcHAiLCJhdWQiOiJkYXQyNTAtZ3ItMi1oMjAyMC1hcHAiLCJhdXRoX3RpbWUiOjE2MDM0NDM5NDQsInVzZXJfaWQiOiJ6dGM5OUF2Z0d4ZkFxMWU0UWxSb1FCNXpJMXMyIiwic3ViIjoienRjOTlBdmdHeGZBcTFlNFFsUm9RQjV6STFzMiIsImlhdCI6MTYwMzQ0NDM5MiwiZXhwIjoxNjAzNDQ3OTkyLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7fSwic2lnbl9pbl9wcm92aWRlciI6ImFub255bW91cyJ9fQ.LKsLnm3GJaUAu9Whrr0tr_VcdycdHlo4ie_T6nTo9URZ7stPydJkQk7a92xkFLSkSMHY8CYLAt3M7V03YD-PIVboPBUpvSs35KKoHMikaKR6Nfbc2X-d7tw874uXBrh6kyqKCAiTc5saIjaBslUbJl5na4L_jkCV2oTjBnBsyq7NbJnPP0a1kJkdPrbxbCVLQV_38CGIj-qhi3tyaveMqoX5tsjD8snIuWvv1iam_hiV_n4ON7Uj4V0ArgDjVz4GYmZh_n2fJtXTZ5wDO5P2usOfftbXLVITHEbLTKnEs4QMaQDJEeHd-MO_AzHTWszRHtL9xXKGLCUVVeFCN0NNCA',
    'Cookie': 'session=eyJhbGciOiJSUzI1NiIsImtpZCI6InRCME0yQSJ9.eyJpc3MiOiJodHRwczovL3Nlc3Npb24uZmlyZWJhc2UuZ29vZ2xlLmNvbS9kYXQyNTAtZ3ItMi1oMjAyMC1hcHAiLCJwcm92aWRlcl9pZCI6ImFub255bW91cyIsImF1ZCI6ImRhdDI1MC1nci0yLWgyMDIwLWFwcCIsImF1dGhfdGltZSI6MTYwMzQ0Mzk0NCwidXNlcl9pZCI6Inp0Yzk5QXZnR3hmQXExZTRRbFJvUUI1ekkxczIiLCJzdWIiOiJ6dGM5OUF2Z0d4ZkFxMWU0UWxSb1FCNXpJMXMyIiwiaWF0IjoxNjAzNDQ0MzkyLCJleHAiOjE2MDM4NzYzOTIsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnt9LCJzaWduX2luX3Byb3ZpZGVyIjoiYW5vbnltb3VzIn19.JajfyYH9tq-iUXawq5a0zi-4Zc-op0ScshL5vsMADX0J3BOAYYfCzVDOkLY1181LxKupHROoF-s1iWfTEQ7YNKQUJgO78NNGqRVWUVl3cj0kE6wYgPmOkSkXcnu3w4TFGye-AJkW7Q5AgEgCt9ruDULVyCNTElZRdh5RfDg81o_kVwL5lrRwDvBFl1vzJma9dXrkDXAftMiPR3Nzq1QkyVofo0hGuVyaU9doYsz-T1NmicFmHglEKYrZZUixmCT71XNLGwExf3K0whBPongh7MS7G5YtnBVKP3zyyrYozyTeB2jcf8GtDxugKd8dASEVEE64jdjJq3sG5QdV4ivP9Q; authenticated=true'
    }
    response = session.request("POST", url, headers=headers, data = {})

    if not response.ok:
        print(response.status_code, response.reason)

def me():
    url = 'http://localhost:8090/api/v1/accounts/me'
    headers = {
    'Content-Type': 'text/plain',
    'Cookie': 'authenticated=true; session=eyJhbGciOiJSUzI1NiIsImtpZCI6InRCME0yQSJ9.eyJpc3MiOiJodHRwczovL3Nlc3Npb24uZmlyZWJhc2UuZ29vZ2xlLmNvbS9kYXQyNTAtZ3ItMi1oMjAyMC1hcHAiLCJwcm92aWRlcl9pZCI6ImFub255bW91cyIsImF1ZCI6ImRhdDI1MC1nci0yLWgyMDIwLWFwcCIsImF1dGhfdGltZSI6MTYwMzQ0Mzk0NCwidXNlcl9pZCI6Inp0Yzk5QXZnR3hmQXExZTRRbFJvUUI1ekkxczIiLCJzdWIiOiJ6dGM5OUF2Z0d4ZkFxMWU0UWxSb1FCNXpJMXMyIiwiaWF0IjoxNjAzNDQ2MTY5LCJleHAiOjE2MDM4NzgxNjksImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnt9LCJzaWduX2luX3Byb3ZpZGVyIjoiYW5vbnltb3VzIn19.M_MIYOV65hBHA2hu6bO0k4z3pDS10ZFwBkRhs_Qd-7Ae-8QftI53vIcGmxY-nkrl8_3e14wlRQc0_i0CUDuZvhuth3r72UdBpvLmnjO52c4RZ2zuWXZDLX-9WyXDd_tC2qXOYfk3uHJfu-1IOj2iaWtkwdnbfdh6ZoFJh6blhyezGODSO3DI2PFsYySp5PFJJHRMrdlsP_0GzAqoJtem9SOjGuyGLoDzoijjealb43if6iuwWpZZ-0ftvcaxQl_FtBg9rxMy9ErBP8moPh_vofH9IcYk7LlE2UGP3TlOzxJwcQKVFnluWz3GNHfzj0UBY5HzrFKL3JbBAz1f2QvRCA'
    }
    response = session.request("GET", url, headers=headers, data = {})
    # print(response.text.encode('utf8'))
    if not response.ok:
        print('me failed:')
        print(response.status_code, response.reason)

def send_votes():
    '''send votes to selected voteid and reset'''
    global first_votes, second_votes
    print('sending votes...')
    pollid = config['POLL']['pollid']
    url = f"http://localhost:8090/api/v1/polls/{pollid}/vote"
    payload = '{"firstVotes": '+str(first_votes)+',"secondVotes": '+str(second_votes)+'}'
    headers = {
    'Content-Type': 'application/json',
    'Cookie': 'authenticated=true; session=eyJhbGciOiJSUzI1NiIsImtpZCI6InRCME0yQSJ9.eyJpc3MiOiJodHRwczovL3Nlc3Npb24uZmlyZWJhc2UuZ29vZ2xlLmNvbS9kYXQyNTAtZ3ItMi1oMjAyMC1hcHAiLCJwcm92aWRlcl9pZCI6ImFub255bW91cyIsImF1ZCI6ImRhdDI1MC1nci0yLWgyMDIwLWFwcCIsImF1dGhfdGltZSI6MTYwMzQ0Mzk0NCwidXNlcl9pZCI6Inp0Yzk5QXZnR3hmQXExZTRRbFJvUUI1ekkxczIiLCJzdWIiOiJ6dGM5OUF2Z0d4ZkFxMWU0UWxSb1FCNXpJMXMyIiwiaWF0IjoxNjAzNDQ2MTY5LCJleHAiOjE2MDM4NzgxNjksImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnt9LCJzaWduX2luX3Byb3ZpZGVyIjoiYW5vbnltb3VzIn19.M_MIYOV65hBHA2hu6bO0k4z3pDS10ZFwBkRhs_Qd-7Ae-8QftI53vIcGmxY-nkrl8_3e14wlRQc0_i0CUDuZvhuth3r72UdBpvLmnjO52c4RZ2zuWXZDLX-9WyXDd_tC2qXOYfk3uHJfu-1IOj2iaWtkwdnbfdh6ZoFJh6blhyezGODSO3DI2PFsYySp5PFJJHRMrdlsP_0GzAqoJtem9SOjGuyGLoDzoijjealb43if6iuwWpZZ-0ftvcaxQl_FtBg9rxMy9ErBP8moPh_vofH9IcYk7LlE2UGP3TlOzxJwcQKVFnluWz3GNHfzj0UBY5HzrFKL3JbBAz1f2QvRCA'
    }
    response = session.request("POST", url, headers=headers, data = payload)
    print(response.text.encode('utf8'))
    first_votes, second_votes = 0, 0

main()