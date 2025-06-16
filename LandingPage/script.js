//mutable Variable
let books = [];
let statisticsGenreCount =[0,0,0,0,0,0];
let statisticName =[]

let yourFavBooks=[]
let rentedBooks=[]
let statsChart = ''


const barColors = [
  "#b91d47",
  "#00aba9",
  "#2b5797",
  "#e8c3b9",
  "#1e7145", 
    "#f5e942"
];
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
        
        `;
        bookContainer.appendChild(bookRow);
    })


        
    statistics()
}

function deleteBook(id){
    books = books.filter( book => book.id !== id)
    saveToLocalStorage();
    displayBooks();
}

let editId = 0


function openModal(id){
    loadFromLocalStorage();
    document.getElementById("modal").style.display = "block";
    editId = id
    // alert(JSON.stringify(books.editId))

    // let newPlaceHolder = document.getElementById("title-edit")
    // newPlaceHolder.placeholder = `not working`

}

const modalEdit = document.getElementById('book-edit-modal')

modalEdit.addEventListener('submit', function(e){
    e.preventDefault();

    const titleEdit = document.getElementById('title-edit').value
    const authorEdit = document.getElementById('author-edit').value
    const publishDateEdit = document.getElementById('publish-date-edit').value
    const genreEdit = document.getElementById('genre-edit').value
    const priceEdit = document.getElementById('price-edit').value

    const bookEdit ={
        id:editId,
        title:titleEdit,
        author:authorEdit,
        publishDate:publishDateEdit,
        genre:genreEdit,
        price:priceEdit,
        rating:rating,
        dateAdded: new Date()
    }
    alert(editId)
    alert(JSON.stringify(bookEdit))

    deleteBook(editId)
    books.push(bookEdit)
    saveToLocalStorage();
    modalEdit.reset();
    displayBooks()
    document.getElementById("modal").style.display = "none";



})


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


let netCount = 0

function statistics(){
    statisticsConrainer.innerHTML = ""
    let statsString = ''
    statisticName[0] = "fiction"
    statisticName[1]= "non-fiction"
    statisticName[2] = "science-fiction"
    statisticName[3] = "fantasy"
    statisticName[4] = "science"
    statisticName[5] = "-- please select a genre --"

    for(let count=0; count<books.length; count++){
        for(let countStatArr = 0; countStatArr<=7; countStatArr++)
        {
        if(books.length === 0){
            alert('Please add more books to see the statistics')
        }else if(books[count].genre === statisticName[countStatArr] ){
            statisticsGenreCount[countStatArr] += 1
        }
        }

        netCount +=1
    }

        
    for(let count = 0; count < statisticsGenreCount.length; count++){
        statsString+= `<td>${((statisticsGenreCount[count]/netCount)*100).toFixed(2)}%</td>`
    }
       
    

    const statisticsCircle = document.createElement('tr');
    statisticsCircle.innerHTML = statsString

    statisticsConrainer.appendChild(statisticsCircle)
    statisticsGenreCount =[0,0,0,0,0,0];
    netCount =0
    
    statsChart = new Chart("myChart",{
        type: "pie",
        data:{
            labels: statisticName,
            datasets:[{
                backgroundColor: barColors,
                data:statisticsGenreCount
            }]
        },
        options: {
            title: {
            display: true
            }
        }
        })

    chart.update();

}




const orderCard = document.getElementById("library-card")
orderCard.addEventListener('submit', function(e){
    e.preventDefault()

    alert('Free Library Card Will be sent to ' + document.getElementById('library-card-email').value)
})

const adFavorite = document.getElementById('your-favorite')
adFavorite.addEventListener('submit',function(e){
    e.preventDefault()
    
    alert('will be added')
    
})

window.addEventListener('DOMContentLoaded', loadFromLocalStorage)
window.addEventListener('DOMContentLoaded', displayBooks)
window.addEventListener('DOMContentLoaded', statistics)