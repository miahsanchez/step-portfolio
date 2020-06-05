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

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List; 
import com.google.gson.Gson;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;

/** Servlet that returns some example content. */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    
  private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // TODO:  Make ArrayList more abstract aka make it a List.
    ArrayList<String> sampleContents = new ArrayList<String>();
    sampleContents.add("My name is Miah");
    sampleContents.add("Im from NYC");
    sampleContents.add("I love coding!");

    // Send the JSON as the response
    String json = convertToJsonUsingGson(sampleContents);
    String jsonComment = new Gson().toJson(sampleContents);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String userIdea = request.getParameter("user-input");
      long timestamp = System.currentTimeMillis();

      Entity youtubeEntity = new Entity("Youtube Idea");
      // Empty String will not be added as an idea, handled on client side.
      youtubeEntity.setProperty("idea", userIdea);
      youtubeEntity.setProperty("timestamp", timestamp);
           
      datastore.put(youtubeEntity);

      response.setContentType("text/html;");
      response.getWriter().println("Thanks for the idea! If I ever make a video about " + userIdea + " you'll be the first to know");

  }
}