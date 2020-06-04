// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['I am 19 years old', 'I am from NYC', 'I make youtube videos', 'I love coding!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/**
 * Adds the button label to the page.
 */
function labelScroll(id, label) {
        document.getElementById(id).innerText = label;
}

/**
 * Removes the button label from the page.
 */
function labelClear(id) {
    document.getElementById(id).innerText = "";
}

/** 
 * Fetches the hello message from the server and adds it to the DOM.
*  TODO: Refactor this code to be more dynamic
 */
function getHello() {
    console.log("fetching the message");
    const helloPromise = fetch("/data");

    helloPromise.then(handleResponse);
}

function handleResponse(response) {
    console.log("handling the response");
    const responsePromise = response.text();

    responsePromise.then(addHelloToDom);
}

function addHelloToDom(hello) {
    console.log("adding hello to DOM: " + hello);

    const helloContainer = document.getElementById('hello-container');
    helloContainer.innerText = hello;
}

function loadComments() {
    fetch('/data').then(response => response.json()).then((ideas) => {
        const ideaListElement = document.getElementById('idea-list');
        ideas.forEach((idea) => {
            ideaListElement.appendChild(createIdeaElement(idea));
        })
    });
}

function createIdeaElement(idea){
    const ideaElement = document.createElement('li');
    // const titleElement = document.createElement('span');
    ideaElement.innerText = idea;
    return ideaElement;
}

function getComments(){
    fetch('/data').then(response => response.json()).then((stats) => {
        document.getElementById('comment1').innerText = stats[0];
        document.getElementById('comment2').innerText = stats[1];
        document.getElementById('comment3').innerText = stats[2];

    });
 }
