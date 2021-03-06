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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.sps.data.Idea;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet responsible for deleting tasks. */
@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

  private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
  private static final Logger LOGGER = Logger.getLogger(VoteServlet.class.getName());

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      try{
        long id = Long.parseLong(request.getParameter("id"));

        Key ideaEntityKey = KeyFactory.createKey("Youtube Idea", id);
        Entity entity = datastore.get(ideaEntityKey); 
        long oldVotes = (long) entity.getProperty("upVotes");
        entity.setProperty("upVotes", oldVotes + 1);
        datastore.put(entity);
      } catch (EntityNotFoundException e) {
        LOGGER.log(Level.WARNING, "Idea entity not found: " + e.getMessage());
      }
  }
}