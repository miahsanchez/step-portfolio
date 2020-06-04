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
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;");
    response.getWriter().println("<h1>Hello Miah!</h1>");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String userIdea = request.getParameter("user-input");

      // Empty String or original textarea string will not be added.
      if (userIdea.trim().equals("") || userIdea.trim().equals("Put your idea here!")) {
          return;
      }
      long timestamp = System.currentTimeMillis();

      Entity youtubeEntity = new Entity("Youtube Idea");
      youtubeEntity.setProperty("idea", userIdea);
      youtubeEntity.setProperty("timestamp", timestamp);
      
      final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();      
      datastore.put(youtubeEntity);

      response.setContentType("text/html;");
      response.getWriter().println("Thanks for the idea! If I ever make a video about " + userIdea + " you'll be the first to know");

  }
}