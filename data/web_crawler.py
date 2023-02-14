import requests
from bs4 import BeautifulSoup
import pandas as pd

first_url = "https://niagarahistorical.pastperfectonline.com"

response = requests.get(first_url + "/WebObject")
url = first_url + "/WebObject"
page_Number = 1
data = []

def item_details(itemurl):
    response = requests.get(itemurl)
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, "html.parser")

        table = soup.find('table')

        cells = []
        for row in table.find_all('tr'):
            for cell in row.find_all('td', class_="display"):
                cells.append(cell.text.strip())
                column_line = ";".join(cells)

        return column_line


def webcrawler(url, page_Number):
    response = requests.get(url)
    if response.status_code == 200:
        soup = BeautifulSoup(response.content, "html.parser")

        search_results = soup.find("div", id="searchresultsdisplay")

        items = search_results.find_all("div", class_="indvImage")
        for item in items:
            image_link = item.find("img")
            image_url = image_link["src"]
            item_detail_link = item.find("a")
            item_link = item_detail_link["href"]
            item_url = first_url + item_link
            table_col = item_details(item_url)
            data_text = image_url + ";" + item_url + ";" + table_col
            data.append(data_text)
    else:
        raise Exception(f"Request failed with status code {response.status_code}")

    while True:
        response = requests.get(url)
        content = response.content
        soup = BeautifulSoup(content, "html.parser")
        next_link = soup.find("a", class_="actionLinkButton", string="Next")
        print(page_Number)
        if page_Number != 89:
            next_url = next_link["href"]
            page_Number += 1
            webcrawler(first_url + next_url, page_Number)
        else:
            break
        return

    df = pd.DataFrame(data, columns=['Data'])
    df.to_csv('/Users/Terry/Downloads/museum map/data/data_4.csv', index=False)


def main():
    webcrawler(url, page_Number)


if __name__ == '__main__':
    main()
