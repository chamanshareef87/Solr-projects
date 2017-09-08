# Solr-projects
This is a sample solrJ client project to get documents from solr as we as number of occurences of each term passed in the input.
This is done using spring-boot

Depenency: This needs a solr server which is already setup and indexed with table
scrapedata:
+----+------------+--------------+---------------------+--------------------------------------------------+
| id | company    | url          | insert_time         | content                                          |
+----+------------+--------------+---------------------+--------------------------------------------------+
| 23 | amazon.com | amazon.com   | 2017-09-07 18:29:22 |                                                  |
| 24 | amazon.com | amazon.com   | 2017-09-07 18:33:21 | router amazon seagate netgear                    |

URL used to call service: http://localhost:8080/getResults?domain=amazon.com&terms=router

