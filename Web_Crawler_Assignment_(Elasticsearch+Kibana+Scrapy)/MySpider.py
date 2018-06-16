import scrapy
from scrapy import Spider
from scrapy.item import Item, Field
from scrapy.contrib.spiders import CrawlSpider, Rule
from scrapy.contrib.linkextractors import LinkExtractor

class MyItem(Item):
    url = Field()
    title = Field()
    description = Field()
    body = Field()

class MySpider(CrawlSpider):
    name = "Huntsman"
    allowed_domains = ["essex.ac.uk"]
    start_urls = ["https://www.essex.ac.uk/"]
    rules = [Rule(LinkExtractor(allow=('/www.essex.ac.uk/((?!:).)*$'),), callback="parse_item", follow=True)]
				
    def parse_item(self, response):
        item = BeastItem()

        url = response.url
        item["url"] = url

        title = response.xpath("//title/text()")[0].extract()
        item["title"] = title

        description = response.xpath("//meta[@name='description']/@content").extract()
        item["description"] = description
        
        body = response.xpath('//body//text()').re(r'(\w[ ,\'\-\w]+\w)')
        item["body"] = body

        return item
