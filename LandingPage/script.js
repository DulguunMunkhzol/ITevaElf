//mutable Variable
let books = [];


//  DOM (Document Object Model) manipulation
//constant or final Variable
const bookForm = document.getElementById('book-form')
const bookContainer = document.getElementById('book-container')

bookForm.addEventListener('submit', function(e){
    e.preventDefault();

    const title = document.getElementById('title').value;
    const author = document.getElementById('author').value;
    const publishDate = document.getElementById('publish-date').value;
    const genre = document.getElementById('genre').value;
    const price = document.getElementById('price').value;

    //javascript object
    const book = {
        id: Date.now(),
        title:title,
        author:author,
        publishDate:publishDate,
        genre:genre,
        price:price,
        rating:0,
        dateAdded: new Date()
    };

    books.push(book);

    bookForm.reset();
    displayBooks();
})

function displayBooks(){
    bookContainer.innerHTML = "";

    if(books.length===0){
        bookContainer.innerHTML = '<p>No books yet, Add your First Book!</p>';
        return;
    }


    books.forEach(book =>{
        const bookRow = document.createElement('tr');
        bookRow.innerHTML = `
        <th>${book.title}</th>
        <td>${book.author}</td>
        <td>${book.publishDate}</td>
        <td>${book.genre}</td>
        <td><button onclick = "deleteBook(${book.id})">Delete</button></td>
        <td><button onclick="editBook(${book.id})">Edit</button></td>
        `;
        bookContainer.appendChild(bookRow);
    })
}

function deleteBook(id){
    books = books.filter( book => book.id !== id)
    displayBooks();
}

function editBook(id){
    alert(id)
}

displayBooks();