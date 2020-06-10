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
    ideaElement.className = 'idea';

    const ideaTitle = document.createElement('span'); 
    ideaTitle.innerText = idea.ideaName;

    const upVoteButton = document.createElement("button");
    upVoteButton.className = "upvote-button";
    upVoteButton.addEventListener('click', () => {
        addVote(idea);
        numVotes.innerText = idea.upVotes + 1;
    });

    const numVotes = document.createElement("p");
    numVotes.innerText = idea.upVotes;

    ideaElement.appendChild(ideaTitle);
    ideaElement.appendChild(upVoteButton);
    ideaElement.appendChild(numVotes);
    return ideaElement;
}

/** Tells the server to increase the upvote value of this idea by 1. */
function addVote(idea) {
    const params = new URLSearchParams();
    params.append('id', idea.id);
    fetch('/vote', {method: 'POST', body: params});
}

var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

var player;
/** Creates an iFrame and youtube player */
function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '390',
        width: '640',
        videoId: 'JPY0FXUs-mM',
        events: {
        'onReady': onPlayerReady,
        'onStateChange': onPlayerStateChange
        }
    });
}

/** Plays video when the video player is ready. */
function onPlayerReady(event) {
    event.target.playVideo();
}

/** Plays the first five seconds of the youtube video. */
var done = false;
function onPlayerStateChange(event) {
    if (event.data == YT.PlayerState.PLAYING && !done) {
        setTimeout(stopVideo, 5000);
        done = true;
    }
}

function stopVideo() {
    player.stopVideo();
}

