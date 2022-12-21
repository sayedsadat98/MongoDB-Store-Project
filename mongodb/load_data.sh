#load transformed data to MongoDB

# create indexes from a script
mongo --host localhost --port 27017 onlinestore_doc_index.js

# use mongoldb database tools to bulk load data, such as:
mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=carrier --file=dataset/carrier.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=category --file=dataset/category.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=customer --file=dataset/customer.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=delivery --file=dataset/delivery.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=item --file=dataset/item.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=orders --file=dataset/orders.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=payment --file=dataset/payment.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=product --file=dataset/product.json

mongoimport --host=localhost --port=27017 --db=onlinestore --jsonArray --collection=supplier --file=dataset/supplier.json

