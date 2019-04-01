import urllib.request
from bs4 import BeautifulSoup

def get_name():
    fp = urllib.request.urlopen("http://www.losownik.pl/imie/losuj/name-surname")
    mybytes = fp.read()
    mystr = mybytes.decode("utf8")
    fp.close()

    return BeautifulSoup(mystr, features="html.parser").find('p', {'class': 'name'}).string.strip()

names = [get_name() for i in range(0, 200)]

with open('names.csv', 'w') as outfile:
    for name in names:
        outfile.write(name.replace(' ', ', '))
        outfile.write('\n')