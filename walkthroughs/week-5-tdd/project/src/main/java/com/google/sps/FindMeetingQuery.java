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

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // this function takes in a collection of events and a meeting request, we are going to 
    // parse through the meeting
    request.getAttendees(); // this is going to be the people required to go to the meeting
    Collection<String> meetingMembers = request.getAttendees();
    Set<Event> relevantEvent = new HashSet<>();
    for (Event e: events) {
        for(String person: e.getAttendees()) {
            if (meetingMembers.contains(person)) {relevantEvent.add(e);}    
        }
    }
    Set<TimeRange> times = new HashSet<>();
    if (request.getDuration() < 24*60){
        times.add(TimeRange.WHOLE_DAY);
    }
    System.out.println("The relevant events are: " + relevantEvent);
    for (Event e: relevantEvent) {
        Set<TimeRange> modifier = new HashSet<>();
        modifier.addAll(times);
        System.out.println("Times:\n" + times);
        for(TimeRange t: times){
            System.out.println("t is: " + t);
            if (t.contains(e.getWhen())){
                System.out.println("Contains");
                System.out.println("e start: " + e.getWhen().start());
                System.out.println("e end: " + e.getWhen().end());
                System.out.println("e duration: " + e.getWhen().duration());
                modifier.remove(t);
                TimeRange beforeContainer = TimeRange.fromStartEnd(t.start(), e.getWhen().start(), false);
                TimeRange afterContainer = TimeRange.fromStartEnd(e.getWhen().end(), t.end(), false);
                if (beforeContainer.duration() > 0) {
                    modifier.add(beforeContainer);
                }
                if (afterContainer.duration() > 0) {
                    modifier.add(afterContainer);
                }
            }
            else if (t.overlaps(e.getWhen())) {
                System.out.println("Overlap");
                System.out.println("e start: " + e.getWhen().start());
                System.out.println("e end: " + e.getWhen().end());
                System.out.println("e duration: " + e.getWhen().duration());
                modifier.remove(t);
                TimeRange remOverlap;
                if (t.start() < e.getWhen().start()) {
                    remOverlap = TimeRange.fromStartEnd(t.start(), e.getWhen().start(), false);
                }else {
                    System.out.println("In the else");
                    remOverlap = TimeRange.fromStartEnd(e.getWhen().end(), t.end(), false);
                }
                modifier.add(remOverlap); 
            }
        }
        System.out.println("Modifier is: " + modifier);
        times.clear();
        times.addAll(modifier);
        System.out.println("Times is: " + times + "\n" );
    }
    Set<TimeRange> enoughTime = new HashSet<>();
    for (TimeRange t: times) {
        if (t.duration() >= request.getDuration()) { 
            enoughTime.add(t);
        }
    }
    ArrayList<TimeRange> timesList = new ArrayList<>(enoughTime);
    Collections.sort(timesList, TimeRange.ORDER_BY_START);
    return timesList;

    /*Dis what im thinking:
        traverse through the events list and if one of the required attendees is in there then
        we get rid of that entire time range
        
        in the beginning we start out with 0-24*60 minutes
        and then we see if there is an overlap between the first event and allat
        and then we keep doing this and making new time ranges!
        and as the times come in we remove them from the range!
        
        Do a time range as**/
  }
}
