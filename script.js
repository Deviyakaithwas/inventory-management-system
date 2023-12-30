async function listItems() {
    // Implement logic to fetch and display items using fetch API or XMLHttpRequest
    const response = await fetch('/listItems');
    const data = await response.json();
    document.getElementById('output').innerHTML = JSON.stringify(data, null, 2);
}

async function addItem() {
    // Implement logic to add an item using fetch API or XMLHttpRequest
    const response = await fetch('/addItem', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            // Provide item details here
        }),
    });
    const data = await response.json();
    alert(data.message);
}
