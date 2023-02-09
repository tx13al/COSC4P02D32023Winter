import requests
from bs4 import BeautifulSoup
first_url = "https://niagarahistorical.pastperfectonline.com"

# Send a GET request to the URL
response = requests.get(first_url + "/WebObject")
url = first_url + "/WebObject"

def webcrawler(url):
    print(url + ";")
    response = requests.get(url)
    if response.status_code == 200:
        # Create a BeautifulSoup object with the HTML content
        soup = BeautifulSoup(response.content, "html.parser")

        # Find the `div` element with the ID "searchresultsdisplay"
        search_results = soup.find("div", id="searchresultsdisplay")

        # Find all the `div` elements with the class "row"
        items = search_results.find_all("div", class_="indvImage")
        images = []
        titles = []
        # Loop through each item
        for item in items:
            # Find the `a` element that contains the image
            image_link = item.find("img")

            # Extract the image URL from the `a` element's `href` attribute
            image_url = image_link["src"]
            image_title = image_link["title"]
            # Do something with the image URL, such as printing it or saving it to a file
            print(image_url+" ; "+image_title+" ; ")
            images.append(image_url)
            titles.append(image_title)


    else:
        # If the response is not successful, raise an exception
        raise Exception(f"Request failed with status code {response.status_code}")
    soup = BeautifulSoup(response.content, "html.parser")
    next_link = soup.find('a', class_="actionLinkButton", string="Next")
    next_url = next_link['href']
    url_1 = first_url + next_url
    while next_link:
        webcrawler(url_1)
    else:
        url_1 = None

webcrawler(url)
