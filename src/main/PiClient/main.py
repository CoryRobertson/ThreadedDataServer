import urllib.request


def main():
    page = urllib.request.urlopen('http://localhost:8123')
    print(page.read())

if __name__ == '__main__':
    main()
