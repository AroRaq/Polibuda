import urllib.request
import random as random
from bs4 import BeautifulSoup

class Generator:

    def get_name(self):
        fp = urllib.request.urlopen("http://www.losownik.pl/imie/losuj/name-surname")
        mybytes = fp.read()
        mystr = mybytes.decode("utf8")
        fp.close()
        return BeautifulSoup(mystr, features="html.parser").find('p', {'class': 'name'}).string.strip()
        
    def generate_names_to_csv(self):
        
        names = [Generator().get_name() for i in range(0, 300)]

        with open('names.csv', 'w') as outfile:
            for name in names:
                outfile.write(name.replace(' ', ', '))
                outfile.write('\n')
    
    def generate_telephone(self):
        return random.randint(100000000, 999999999)

    def generate_date(self, startYear, endYear):
        return str(random.randint(1, 28))+'/'+str(random.randint(1, 12))+'/'+str(random.randint(startYear, endYear))

for i in range(1, 100):
    print(Generator().generate_date(2008, 2011))

#Generator().generate_names_to_csv()