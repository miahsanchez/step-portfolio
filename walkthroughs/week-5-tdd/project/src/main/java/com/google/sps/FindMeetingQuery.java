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
    Set<TimeRange> times = new HashSet<>();
    if (request.getDuration() < 24*60){
        times.add(TimeRange.WHOLE_DAY);
    }
    for (Event e: events) {
        Set<TimeRange> modifier = new HashSet<>();
        modifier.addAll(times); // i could also do something where I order the events from start to end and then just add the gaps in between them
        if (request.getAttendees().containsAll(e.getAttendees())){
        for(TimeRange t: times){
            if (t.contains(e.getWhen())){
                modifier.remove(t);
                TimeRange beforeContainer = TimeRange.fromStartEnd(t.start(), e.getWhen().start(), false);
                TimeRange afterContainer = TimeRange.fromStartEnd(e.getWhen().end(), t.end(), false);
                if (beforeContainer.duration() >= request.getDuration()) {
                    modifier.add(beforeContainer);
                }
                if (afterContainer.duration() >= request.getDuration()) {
                    modifier.add(afterContainer);
                }
            }
            else if (t.overlaps(e.getWhen())) {
                modifier.remove(t);
                TimeRange remOverlap;
                if (t.start() < e.getWhen().start()) {
                    remOverlap = TimeRange.fromStartEnd(t.start(), e.getWhen().start(), false);
                }else {
                    remOverlap = TimeRange.fromStartEnd(e.getWhen().end(), t.end(), false);
                }
                if (remOverlap.duration() >= request.getDuration()){
                modifier.add(remOverlap);}
            }
        }
        times.clear();
        times.addAll(modifier);
    }}
    ArrayList<TimeRange> timesList = new ArrayList<>(times);
    Collections.sort(timesList, TimeRange.ORDER_BY_START);
    return timesList;
  }
}
