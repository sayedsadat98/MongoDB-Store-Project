var MongoClient = require("mongodb").MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function (err, db) {
  if (err) throw err;
  var dbo = db.db("monga");
  dbo.createCollection("carrier", function (err, res) {
    if (err) throw err;
    console.log("carrier collection created!");
    db.close();
  });
  dbo.createCollection("category", function (err, res) {
    if (err) throw err;
    console.log("category collection created!");
    db.close();
  });
  dbo.createCollection("customer", function (err, res) {
    if (err) throw err;
    console.log("customer collection created!");
    db.close();
  });
  dbo.createCollection("delivery", function (err, res) {
    if (err) throw err;
    console.log("delivery collection created!");
    db.close();
  });
  dbo.createCollection("item", function (err, res) {
    if (err) throw err;
    console.log("item collection created!");
    db.close();
  });
  dbo.createCollection("orders", function (err, res) {
    if (err) throw err;
    console.log("orders collection created!");
    db.close();
  });
  dbo.createCollection("payment", function (err, res) {
    if (err) throw err;
    console.log("payment collection created!");
    db.close();
  });
  dbo.createCollection("product", function (err, res) {
    if (err) throw err;
    console.log("product collection created!");
    db.close();
  });
  dbo.createCollection("supplier", function (err, res) {
    if (err) throw err;
    console.log("supplier collection created!");
    db.close();
  });
});

// print("ok");
// db.stats();
