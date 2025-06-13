//mutable Variable
let books = [];
let statisticsGenreCount =[0,0,0,0,0,0,0];
let statisticName =[]

let yourFavBooks=[]

//  DOM (Document Object Model) manipulation
//constant or final Variable
const bookForm = document.getElementById('book-form')
const bookContainer = document.getElementById('book-container')
const search = document.getElementById('search');


const statisticsConrainer = document.getElementById('statistics-container');


bookForm.addEventListener('submit', function(e){
    e.preventDefault();

    const title = document.getElementById('title').value;
    const author = document.getElementById('author').value;
    const publishDate = document.getElementById('publish-date').value;
    const genre = document.getElementById('genre').value;
    const price = document.getElementById('price').value;
    const rating = document.getElementById('rating').value;

    //javascript object
    const book = {
        id: Date.now(),
        title:title,
        author:author,
        publishDate:publishDate,
        genre:genre,
        price:price,
        rating:rating,
        dateAdded: new Date()
    };

    books.push(book);
    saveToLocalStorage();
    bookForm.reset();
    displayBooks();
})




function createStarRating(bookId, rating){
    const maxRating = 5
    let starString =   ''
    let count = 1
    for(; count <= maxRating; count++ ){
        starString += `<span class ="${count <= rating ? 'gold-star':'' }" onclick="addRating(${bookId}, ${count})"> &#9733;</span>`
    }

    return starString;
}

function addRating(id, rating){
    const book=books.find(book=>book.id===id);
    if(book){
        book.rating=rating
        saveToLocalStorage();
        displayBooks();
    }
}

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
        <td>${createStarRating(book.id,book.rating)}</td>
        <td><button onclick = "deleteBook(${book.id})">Delete</button></td>
        <td><button id="edit-modal" onclick="openModal(${book.id})">Edit</button></td> 
        <div id="modal" class="modal">Edit Modal

        <button onclick="closeModal()" id = "modal-close-button">X</button>

        <form id="book-edit-modal">
            <input type="text" id="title-edit" placeholder="Edit Title" required>
            <input type="text" id="author-edit" placeholder="Edit Author" required>
            <input type="date" id="publish-date-edit" placeholder="Edit Published Date" required>
            <input type="number" id="price-edit" placeholder="Edit Price " required>
            <select name="genre-edit" id="genre-edit">
                <option>-- please select a genre --</option>
                <option value="fiction">Fiction</option>
                <option value="non-fiction">Non-Fiction</option>
                <option value="science-fiction">Science Fiction</option>
                <option value="fantasy">Fantasy</option>
                <option value="science">Science</option>
            </select>
            <input type="submit" value="Edit Book">
        </form>

        </div>
        `;
        bookContainer.appendChild(bookRow);
    })
}

function deleteBook(id){
    books = books.filter( book => book.id !== id)
    saveToLocalStorage();
    displayBooks();
}


function openModal(idEdited){
    const modalEdit = document.getElementById('book-edit-modal')

    document.getElementById("modal").style.display = "block";

    modalEdit.addEventListener('submit', function(e){
        e.preventDefault();


        const titleEdit = document.getElementById('title-edit').value
        const authorEdit = document.getElementById('author-edit').value
        const publishDateEdit = document.getElementById('publish-date-edit').value
        const genreEdit = document.getElementById('genre-edit').value
        const priceEdit = document.getElementById('price-edit').value

        const bookEdit ={
            id:idEdited,
            title:titleEdit,
            author:authorEdit,
            publishDate:publishDateEdit,
            genre:genreEdit,
            price:priceEdit,
            rating:rating,
            dateAdded: new Date()
        }
        books = books.filter(book =>book.id !== id)
        books.push(bookEdit)
        saveToLocalStorage();
        modalEdit.reset();
        displayBooks()
    })
}



function closeModal(){
    document.getElementById("modal").style.display= "none";
}


function sortRating(){
    if(books.length===0){
        alert("Add More Books to Sort")
    }else{
        books.sort((a,b)=>(b.rating-a.rating))
        displayBooks();
    }

}


function sortByAuthor(){
    if(books.length===0){
        alert("Add more Books to Sort")
    }else{
        books.sort((a,b)=>a.author.localeCompare(b.author));
        displayBooks();    
    }
}




function sortByTitle(){
    if(books.length===0){
        alert("Add more Books to Sort")
    }else{
        books.sort((a,b)=>a.title.localeCompare(b.title));
        displayBooks();    
    }
}


const searchBooks = () =>{
    const searchTerm = document.getElementById("search").value.toLowerCase();

    const filteredBooks = books.filter(book => 
        book.title.toLowerCase().includes(searchTerm) || 
        book.author.toLowerCase().includes(searchTerm) || 
        book.genre.toLowerCase().includes(searchTerm)
    );

    displayFilteredBooks(filteredBooks);
}

search.addEventListener('keypress', function(e){
    if(e.key === 'Enter'){
    e.preventDefault;
    searchBooks();
    }
})

const displayFilteredBooks =(filteredBooks)=>{
    const searchResults = document.getElementById('search-results');
    searchResults.innerHTML = '';
    



    const searchList = document.createElement('ol');

    if(filteredBooks.length===0){ //something wrong here

        const errorMessage = document.createElement("p")
        errorMessage.innerHTML = 'No Results Found!'
        searchResults.appendChild(errorMessage);
        return;
    }


    searchList.setAttribute('type', 'I');

    filteredBooks.forEach(book =>{
        const listItem = document.createElement('li');
        listItem.innerHTML = `
        <div>
            <h4>${book.title}</h4>
            <h5>${book.author}</h5>
            <p>${book.publishDate}</p>
            <p>${book.genre}</p>
            <p>${book.rating}</p>
        </div>
        `;

        searchList.appendChild(listItem)
    })
    searchResults.appendChild(searchList);
}

function saveToLocalStorage(){
    localStorage.setItem('bookHubBooks', JSON.stringify(books));
}

function loadFromLocalStorage(){
    const saved = localStorage.getItem('bookHubBooks');
    
    if(saved){
        books = JSON.parse(saved);

        books.forEach(book => book.dateAdded = new Date(book.dateAdded))/* inline */
        displayBooks();
    }

}




function statistics(){
    statisticsConrainer.innerHTML = ""
    let statsString = ''
    statisticName[0] = "fiction"
    statisticName[1]= "non-fiction"
    statisticName[2] = "science-fiction"
    statisticName[3] = "fantasy"
    statisticName[4] = "science"
    statisticName[5] = "-- please select a genre --"
    statisticName[6] = "combined"

    for(let count=0; count<books.length; count++){
        for(let countStatArr = 0; countStatArr<=7; countStatArr++)
        {
        if(books.length === 0){
            alert('Please add more books to see the statistics')
        }else if(books[count].genre === statisticName[countStatArr] ){
            statisticsGenreCount[countStatArr] += 1
        }
        }

        statisticsGenreCount[6] +=1
    }

        
    for(let count = 0; count < statisticsGenreCount.length; count++){
        statsString+= `<td>${((statisticsGenreCount[count]/statisticsGenreCount[6])*100).toFixed(2)}%</td>`
    }

    

    const statisticsCircle = document.createElement('tr');
    statisticsCircle.innerHTML = statsString

    statisticsConrainer.appendChild(statisticsCircle)
    statisticsGenreCount =[0,0,0,0,0,0,0];
    
    
}

const orderCard = document.getElementById("library-card")
orderCard.addEventListener('submit', function(e){
    e.preventDefault()

    alert('Free Library Card Will be sent to ' + document.getElementById('library-card-email').value)
})

const adFavorite = document.getElementById('your-faveorite')
adFavorite.addEventListener('submit',function(e){
    e.preventDefault();
    
})

window.addEventListener('DOMContentLoaded', loadFromLocalStorage)
window.addEventListener('DOMContentLoaded', displayBooks)
window.addEventListener('DOMContentLoaded', statistics)