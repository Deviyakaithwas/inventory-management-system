const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const port = 3000;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Your existing inventory management logic goes here

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});

app.get('/listItems', (req, res) => {
    // Implement logic to return the list of items
    res.json({ items: [] });
});

app.post('/addItem', (req, res) => {
    // Implement logic to add an item
    // Access item details using req.body
    res.json({ message: 'Item added successfully!' });
});
