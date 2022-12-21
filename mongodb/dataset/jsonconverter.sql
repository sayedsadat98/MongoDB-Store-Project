SET @jsoncarrier=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "name", name, "contacts",contacts)) 
    FROM carrier);
    
SELECT JSON_PRETTY(@jsoncarrier);

SET @jsoncategory=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "description", description)) 
    FROM category);
    
SELECT JSON_PRETTY(@jsoncategory);

SET @jsoncustomer=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "name", name, "contacts",contacts)) 
    FROM customer);
    
SELECT JSON_PRETTY(@jsoncustomer);

SET @jsondelivery=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "carrier", carrier, "deliveryDate",deliveryDate)) 
    FROM delivery);
    
SELECT JSON_PRETTY(@jsondelivery);

SET @jsonitem=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "quantity", quantity, "product",product, "orders", orders)) 
    FROM item);
    
SELECT JSON_PRETTY(@jsonitem);

SET @jsonorders=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "totalPrice", totalPrice, "date",date, "saleDate", saleDate, "customer", customer, "payment", payment)) 
    FROM orders);
    
SELECT JSON_PRETTY(@jsonorders);

SET @jsonpayment=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "paymentDate", paymentDate)) 
    FROM payment);
    
SELECT JSON_PRETTY(@jsonpayment);


SET @jsonproduct=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "price", price, "description", description, "category", category, "supplier", supplier)) 
    FROM product);
    
SELECT JSON_PRETTY(@jsonproduct);

SET @jsonsupplier=(SELECT JSON_ARRAYAGG(
    JSON_OBJECT("id", id, "name", name, "contacts", contacts)) 
    FROM supplier);
    
SELECT JSON_PRETTY(@jsonsupplier);