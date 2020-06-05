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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import java.util.List; 
import com.google.gson.Gson;
import com.google.sps.data.Idea;

/** Servlet that returns some example content. */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    
  private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
  private final Gson gson = new Gson();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Youtube Idea").addSort("timestamp", SortDirection.ASCENDING);

    PreparedQuery results = datastore.prepare(query);

    List<Idea> ideas = new ArrayList<>();
    for (Entity entity: results.asIterable()) {
        long id = entity.getKey().getId();
        String ideaName = (String) entity.getProperty("idea");
        long timestamp = (long) entity.getProperty("timestamp");
        long upVotes = (long) entity.getProperty("upVotes");

        Idea newIdea = new Idea(id, ideaName, timestamp, upVotes);
        ideas.add(newIdea);
    }

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(ideas));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String userIdea = request.getParameter("user-input");
      long timestamp = System.currentTimeMillis();

      Entity youtubeEntity = new Entity("Youtube Idea");
      // Empty String will not be added as an idea, handled on client side.
      youtubeEntity.setProperty("idea", userIdea);
      youtubeEntity.setProperty("timestamp", timestamp);
      youtubeEntity.setProperty("upVotes", 0);
           
      datastore.put(youtubeEntity);

      response.sendRedirect("/youtube.html");
  }
}