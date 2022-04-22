import urllib.request
import sys

def main():
    args = sys.argv
    print(args)
    #page = urllib.request.urlopen('http://localhost:8123')
    #print(page.read())
    #TODO: maybe skip the webserver and instead simply take command line args, prob way easier
    #TODO: do something based on some inputs to allow the web server read output determine something

if __name__ == '__main__':
    main()
