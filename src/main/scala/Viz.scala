package Viz

import org.scalajs.dom
import dom.document
import scala.scalajs.js.Date
import org.scalajs.d3v4.d3

import org.scalajs.dom.ext.Ajax
import scala.concurrent._
import ExecutionContext.Implicits.global

import scalatags.Text.all._

object Viz {

  case class Margin(top: Int, right: Int, bottom: Int, left: Int)
  case class Paper(uid: String,
                   title: String,
                   issn: String,
                   subject: String,
                   ef: Float,
                   pub_date: Date,
                   r_date: Date,
                   peak: Int,
                   lag: Float,
                   h_life: Float,
                   A: Float,
                   K: Float,
                   cites: List[Int])

  def main(args: Array[String]): Unit = {

    //loadData("./data.csv")

    // margin of visualization
    val margin = Margin(20, 20, 60, 80)

    def getDim(element: dom.Element): (Int, Int) = {
      (element.clientWidth, element.clientHeight)
    }
    val (w1, h1) = getDim(document.getElementById("heat"))
    val (w2, h2) = getDim(document.getElementById("time"))

    val heat = d3.select("#scatter").append("svg")
      .attr("width", w1 + margin.left + margin.right)
      .attr("height", h1 + margin.top + margin.bottom)
      .append("g").attr("transform",
        "translate(" + margin.left + "," + margin.top + ")")

    val bar = d3.select("#bar").append("svg")
      .attr("width", w1 + margin.left + margin.right)
      .attr("height", h1 + margin.top + margin.bottom)
      .append("g").attr("transform",
        "translate(" + margin.left + "," + margin.top + ")")

    val time = d3.select("#time").append("svg")
      .attr("width", w2 + margin.left + margin.right)
      .attr("height", h2 + margin.top + margin.bottom)
      .append("g").attr("transform",
        "translate(" + margin.left + "," + margin.top + ")")

    val tooltip = d3.select("#tooltip")
  }

  def tooltipText(paper: Paper, journals: Map[String, String]) {
    span(strong(paper.title) + "</br>" +
      strong("Published:") + paper.pub_date + "<br/>" +
      strong("Retracted:") + paper.r_date + "<br/>" +
      journals(paper.issn) + "<br/>" + paper.subject)
  }

  def loadData(url: String): Unit = {
    val xhr = new dom.XMLHttpRequest()
    xhr.open("GET", url)
    xhr.onload = (e: dom.Event) => {
      if (xhr.status == 200) {
        println(xhr.responseText)
      }
    }
  }

//
//$("#date-start").append($("<option>", {
//    class: "option-date-start",
//    value: "start",
//    text: "start",
//    selected: true,
//    disabled: true
//}));
//
//$("#date-end").append($("<option>", {
//    class: "option-date-end",
//    value: "end",
//    text: "end",
//    selected: true,
//    disabled: true
//}));
//
//let dateStart = d3.select("#date-start");
//let dateEnd = d3.select("#date-end");
//for (i = 1900; i < 2017; i++) {
//    dateStart.append("option")
//        .classed("option-date-start", true)
//        .attr("value", i)
//        .text(i);
//
//    dateEnd.append("option")
//        .classed("option-date-end", true)
//        .attr("value", i)
//        .text(i);
//}
//

//function tooltipText(d) {
//    return "<strong>" + d.title + "</strong>" + "<br/>" + "<strong>Published:</strong> " + d.pub + ", <strong>Retracted:</strong> " + d.retr + "<br/>" + journalMap[d.issn] + "<br/>" + d.subject;
//}
//
//function getColor(d) {
//    return d3.interpolateRdYlBu(1 - Math.min(d / 25, 1));
//}
//
//var journalMap = {};
//d3.csv("journals-cite-count.csv", function(error, journals) {
//    if (error) { throw error; }
//    journals.forEach(processJournal);
//});
//
//$("#issn-sort").change( (event) => {
//    journalMap = {};
//    let selection = $("#issn-sort option:selected").attr("id")
//    d3.csv("journals-" + selection + ".csv", function(error, journals) {
//        if (error) { throw error; }
//        d3.selectAll("#issns li").remove();
//        journals.forEach(processJournal);
//    });
//});
//
//var filteredJournals = {"ALL": "blue"};
//function processJournal(j) {
//    journalMap[j.issn] = j.title;
//    d3.select("#issns").append("li")
//        .attr("id", j.issn)
//        .text(j.title)
//        .attr("class", () => {
//            if (filteredJournals.hasOwnProperty(j.issn)) {
//                return "clicked " + filteredJournals[j.issn];
//            } else { return ""; }
//        }).on("mouseover", function() { d3.select(this).classed("hover", true);
//        }).on("mouseout", function() { d3.select(this).classed("hover", false);
//        }).on("click", function() { journalClick(d3.select(this)); });
//}
//
////d3.csv("subjects.csv", function(error, subjects) {
////    if (error) { throw error; }
////    subjects.forEach( (s) => {
////       $("#subjects").append($("<li>", {
////            class: s.subject,
////            text: s.subject
////        }));
////    });
////});
//
//// read cite graph from csv
////var cite_graph = {};
////d3.csv("retract_edges.csv", function(error, retract_edges) {
////    if (error) { throw error; }
////    retract_edges.forEach( (e) => {
////        if(cite_graph[e.cited]) {
////            cite_graph[e.cited].push(e.citing);
////        } else {
////            cite_graph[e.cited] = [e.citing];
////        }
////    });
////});
//
//// parse the date / time
//var parseTime = d3.timeParse("%Y-%m-%d");
//var timeFormat = d3.timeFormat("%Y")
//
//var points;
//var retractLines;
//var citeLine;
//var decayLine;
//var overlayBars;
//var journalClick;
//d3.csv("data.csv", function(error, data) {
//    if (error) { throw error; }
//    // preprocess data
//    data.forEach( (r) => {
//        r.pub = r.pub_date;
//        r.retr = r.r_date;
//        r.pub_date = parseTime(r.pub_date);
//        r.r_date = parseTime(r.r_date);
//        //r.lag = +r.lag;
//        r.ef = +r.ef;
//        r.lag = +r.lag;
//        r.peak = +r.peak;
//        r.h_life = +r.h_life;
//        r.A = +r.A;
//        r.K = +r.K;
//        r.cites = JSON.parse(r.cites.replace(/(\d)\s/g,"$1,"))
//    });
//
//    // before vs after scatter plot
//    let xDomain = d3.extent(data, (r) => { return r.lag; });
//    let yDomain = d3.extent(data, (r) => { return r.peak - parseInt(timeFormat(r.pub_date)); });
//
//    let scatterX = d3.scaleLinear()
//        .range([0, width])
//        .domain(xDomain);
//
//    let scatterY = d3.scaleLinear()
//        .range([height, 0])
//        .domain(yDomain);
//
//    scatter.append("g")
//        .classed("x-axis", true)
//        .attr("transform", "translate(0," + height + ")")
//        .call(d3.axisBottom(scatterX));
//
//    scatter.append("g")
//        .classed("y-axis", true)
//        .call(d3.axisLeft(scatterY));
//
//    scatter.append("text")
//        .attr("text-anchor", "middle")
//        .attr("transform", "translate(" + (-margin.left/2) + "," + (height/2) + ")rotate(-90)")
//        .text("years until citation peak")
//        .classed("axis-label", true);
//
//    scatter.append("text")
//        .attr("text-anchor", "middle")
//        .attr("transform", "translate(" + (width/2) + "," + (height+(2*margin.bottom/3)) + ")")
//        .text("years until retraction")
//        .classed("axis-label", true);
//
//    scatter.selectAll(".point")
//        .data(data)
//        .enter().append("circle")
//        .classed("point blue", true)
//        .attr("cx", (d) => {
//            return scatterX(d.lag); })
//        .attr("cy", (d) => { return scatterY(d.peak - parseInt(timeFormat(d.pub_date))); })
//        .on("mouseover", pointHover)
//        .on("mouseout", pointLeave)
//        .on("click", pointClick);
//
//    // draw diagonal line on scatter plot
//    let b = Math.max(xDomain[0], yDomain[0])
//    let t = Math.min(xDomain[1], yDomain[1])
//    let line = scatter.append("line")
//        .classed("diag-line", true)
//        .attr("x1", scatterX(b))
//        .attr("y1", scatterY(b))
//        .attr("x2", scatterX(t))
//        .attr("y2", scatterY(t))
//        .style("stroke-dasharray", ("5, 5"))
//        .style("stroke", "gray");
//
//    // bottom time plot
//    timeX = d3.scaleTime()
//        .range([0, bWidth])
//        .domain([d3.min(data, (d) => { return d.pub_date; }), d3.max(data, (d) => { return d.r_date; })]);
//
//    timeY = d3.scaleLog()
//        .range([bHeight, 0])
//        .domain(d3.extent(data, (d) => { return d.ef; }));
//
//
//    time.append("g")
//        .classed("x-axis", true)
//        .attr("transform", "translate(0," + bHeight + ")")
//        .call(d3.axisBottom(timeX));
//
//    time.append("g")
//        .classed("y-axis", true)
//        .call(d3.axisLeft(timeY));
//
//    time.append("text")
//        .attr("text-anchor", "middle")
//        .attr("transform", "translate(" + (-margin.left/2) + "," + (bHeight/2) + ")rotate(-90)")
//        .text("Eigenfactor score (log scale)")
//        .classed("axis-label", true);
//
//    time.append("text")
//        .attr("text-anchor", "middle")
//        .attr("transform", "translate(" + (bWidth/2) + "," + (bHeight+(2*margin.bottom/3)) + ")")
//        .text("year")
//        .classed("axis-label", true);
//
//    // Add the scatterplot
//    time.selectAll(".point")
//        .data(data)
//        .enter().append("circle")
//        .classed("point blue", true)
//        .attr("cx", (d) => { return timeX(d.pub_date); })
//        .attr("cy", (d) => { return timeY(d.ef); })
//        .on("mouseover", pointHover)
//        .on("mouseout", pointLeave)
//        .on("click", pointClick);
//
//    // select all points for both plots
//    points = d3.select("#vis").selectAll(".point");
//
//    retractLines = time.selectAll(".retract-line")
//        .data(data)
//        .enter().append("line")
//        .classed("line", true)
//        .attr("x1", d => { return timeX(d.r_date); })
//        .attr("x2", d => { return timeX(d.r_date); })
//        .attr("y1", bHeight)
//        .attr("y2", 0)
//        .style("opacity", 0);
//
//    let barX = d3.scaleLinear()
//        .rangeRound([0, width])
//        .domain([0, d3.max(data, (d) => { return Math.ceil(d.h_life); })]);
//
//    let histogram = d3.histogram()
//        .value( (d) => { return d.h_life; })
//        .domain(barX.domain())
//        .thresholds(barX.ticks(d3.max(data, (d) => { return d.h_life; })));
//
//    let bins = histogram(data);
//
//    let barY = d3.scaleLinear()
//        .range([height, 0])
//        .domain([0, d3.max(bins, (b) => { return b.length; })]);
//
//    bar.append("g")
//        .classed("x-axis", true)
//        .attr("transform", "translate(0," + height + ")")
//        .call(d3.axisBottom(barX));
//
//    bar.append("g")
//        .classed("y-axis", true)
//        .call(d3.axisLeft(barY));
//
//    bar.append("text")
//        .attr("text-anchor", "middle")
//        .attr("transform", "translate(" + (-margin.left/2) + "," + (height/2) + ")rotate(-90)")
//        .text("number of papers")
//        .classed("axis-label", true);
//
//    bar.append("text")
//        .attr("text-anchor", "middle")
//        .attr("transform", "translate(" + (width/2) + "," + (height+(2*margin.bottom/3)) + ")")
//        .text("retraction lag (years)")
//        .classed("axis-label", true);
//
//    let bars = bar.selectAll("rect.bar")
//        .data(bins)
//        .enter().append("rect")
//        .classed("bar blue", true)
//        .style("width", (b) => { return barX(b.x1) - barX(b.x0) - 1; })
//        .style("height", (b) => { return height - barY(b.length); })
//        .attr("x", (b) => { return barX(b.x0); })
//        .attr("y", (b) => { return barY(b.length); });
//
//    overlayBars = bar.selectAll("rect.bar.overlay")
//        .data(bins)
//        .enter().append("rect")
//        .classed("bar overlay", true)
//        .style("width", (b) => { return barX(b.x1) - barX(b.x0) - 1; })
//        .style("height", (b) => { return height - barY(b.length); })
//        .attr("x", (b) => { return barX(b.x0); })
//        .attr("y", (b) => { return barY(b.length); })
//        .on("click", barClick);
//
//    let colors = ["red", "blue", "green", "purple", "orange", "brown", "pink", "grey"];
//    let filteredData = data;
//    journalClick = function(e) {
//        let issn = e.attr("id");
//        // if color is being removed
//        if (issn != "ALL" && e.classed("clicked")) {
//            // get color from class
//            let color = e.attr("class")
//                .replace("hover", "")
//                .replace("clicked", "")
//                .replace(/ /g, "");
//            colors.unshift(color);
//            // clear elements of that color
//            d3.select("#vis").selectAll(".point." + color).remove();
//            bar.selectAll("rect.bar." + color).remove();
//            e.attr("class", null);
//            delete filteredJournals[issn];
//            filteredData = filteredData.filter((d) => { return d.issn != issn; });
//        }
//        // if color is being added
//        else {
//            let color = "blue";
//            let newPoints = data;
//            if (issn == "ALL") {
//                colors = ["red", "blue", "green", "purple", "orange", "brown", "pink", "grey"];
//                d3.selectAll("li.clicked").attr("class", null);
//                d3.select("#vis").selectAll("circle.point").remove();
//                bar.selectAll("rect.bar:not(.overlay)").remove();
//                filteredJournals = {"ALL": "blue"};
//                filteredData = data;
//            } else if (colors.length > 0) {
//                // clear all points before re-adding
//                if (filteredJournals.hasOwnProperty("ALL")) {
//                    d3.select("li#ALL").classed("clicked blue", false);
//                    d3.select("#vis").selectAll("circle.point").remove();
//                    bar.selectAll("rect.bar:not(.overlay)").remove();
//                    delete filteredJournals["ALL"];
//                    console.log("All selected");
//                }
//                color = colors.shift();
//                filteredJournals[issn] = color;
//                newPoints = data.filter((d) => { return d.issn == issn; });
//                filteredData = data.filter((d) => {
//                    return filteredJournals.hasOwnProperty(d.issn);
//                });
//            } else { return; }
//            e.classed("clicked " + color, true)
//            scatter.selectAll("circle." + color)
//                .data(newPoints).enter().append("circle")
//                .classed(color + " point", true)
//                .attr("cx", (d) => { return scatterX(d.b_cites); })
//                .attr("cy", (d) => { return scatterY(d.a_cites); })
//                .on("mouseover", pointHover)
//                .on("mouseout", pointLeave)
//                .on("click", pointClick);;
//            time.selectAll("circle." + color)
//                .data(newPoints).enter().append("circle")
//                .classed(color + " point", true)
//                .attr("cx", (d) => { return timeX(d.pub_date); })
//                .attr("cy", (d) => { return timeY(d.ef); })
//                .on("mouseover", pointHover)
//                .on("mouseout", pointLeave)
//                .on("click", pointClick);
//        }
//
//        overlayBins = histogram(filteredData);
//        let extent = d3.extent(overlayBins, (b) => { return b.length; });
//        barY.domain(extent);
//
//        bar.select(".y-axis")
//            .transition(1500)
//            .call(d3.axisLeft(barY));
//
//        overlayBars = bar.selectAll(".bar.overlay")
//            .data(overlayBins)
//            .attr("class", "bar overlay")
//            .style("width", (b) => { return barX(b.x1) - barX(b.x0) - 1; })
//            .style("height", (b) => { return height - barY(b.length); })
//            .attr("x", (b) => { return barX(b.x0); })
//            .attr("y", (b) => { return barY(b.length); })
//            .on("click", barClick);
//
//        let offset = Array(overlayBins.length).fill(0);
//        for (let issn in filteredJournals) {
//            if (filteredJournals.hasOwnProperty(issn)) {
//                if (issn == "ALL") {
//                    bins = histogram(filteredData);
//                    let b = bar.selectAll(".bar.blue").data(bins);
//                    b.enter().append("rect")
//                        .attr("x", (b) => { return barX(b.x0); })
//                        .attr("y", (b) => { return barY(b.length); })
//                        .style("height", (b) => { return height - barY(b.length); })
//                        .style("width", (b) => { return barX(b.x1) - barX(b.x0) - 1; })
//                        .attr("class", "bar blue");
//                } else {
//                    bins = histogram(data.filter((d) => { return d.issn == issn; }));
//                    let b = bar.selectAll(".bar." + filteredJournals[issn]).data(bins);
//                    b.enter().append("rect").merge(b)
//                        .attr("class", (b) => { return "bar " + filteredJournals[issn]; })
//                        .attr("x", (b) => { return barX(b.x0); })
//                        .style("width", (b) => { return barX(b.x1) - barX(b.x0) - 1; })
//                        .attr("y", (b) => {
//                            var o = offset[b.x0];
//                            offset[b.x0] = o + b.length;
//                            return barY(b.length + o); })
//                        .style("height", (b) => { return height - barY(b.length); });
//                }
//            }
//        }
//        overlayBars.moveToFront();
//        points = d3.select("#vis").selectAll(".point");
//    }
//
//    function pointClick(d) {
//        if (clicked) {
//            clearSelection();
//            clicked = null;
//            citing = null;
//            rLine = null;
//        } else {
//            clicked = d;
//
//            // draw citing line
//            var timeYLinear = d3.scaleLinear()
//                .range([0, bHeight])
//                .domain([Math.max.apply(null, d.cites), 0]);
//
//            time.append("g")
//                .classed("y-axis-right", true)
//                .attr("transform", "translate(" + bWidth + ")")
//                .call(d3.axisLeft(timeYLinear));
//
//            time.append("text")
//                .classed("axis-label-right", true)
//                .attr("text-anchor", "middle")
//                .attr("transform", "translate(" + (bWidth - margin.left/2) + "," + (bHeight/2) + ")rotate(-90)")
//                .text("citations");
//
//            var line = d3.line()
//                .x((d) => { return timeX(d.year); })
//                .y((d) => { return timeYLinear(d.cites); });
//
//            var lineData = [];
//            var decayData = [];
//            var year = timeFormat(d.pub_date);
//            console.log(d)
//            for (var i = 0; i < d.cites.length; i++) {
//                var y = parseInt(year) + i
//                lineData.push({"year": parseTime(y + "-01-01"), "cites": d.cites[i]});
//                if (y >= d.peak) {
//                    console.log(y)
//                    console.log(d.A * Math.pow(Math.E, d.K*(y-d.peak)));
//                    decayData.push({"year": parseTime(y + "-01-01"), "cites": d.A * Math.pow(Math.E, d.K*(y-d.peak))});
//                }
//            }
//
//            citeLine = time.append("path")
//                .attr("class", "cite-line")
//                .attr("d", line(lineData));
//
//            decayLine = time.append("path")
//                .attr("class", "decay-line")
//                .attr("d", line(decayData));
//
//            points.transition(200)
//                .style("opacity", (o) => { return o.uid == d.uid ? 1 : 0.1; });
//
//            rLine.transition(200)
//                .style("stroke-width", 2)
//                .style("opacity", 1);
//
//            tooltip.transition()
//                .duration(200)
//                .style("opacity", 0.9);
//
//            tooltip.html(tooltipText(d));
//        }
//    }
//
//        // draw axes
//        //if (!startDate) {
//        //    startDate = d3.min(fData, (d) => { return d.pub_date; });
//        //}
//        //if (!endDate) {
//        //    endDate = d3.max(fData, (d) => { return d.r_date; });
//        //}
//        //timeX.domain([startDate, endDate]);
//        //time.select(".x-axis")
//        //    .call(d3.axisBottom(timeX));
//
//        //timeY.domain(d3.extent(data, (d) => {return d.ef; }));
//        //time.select(".y-axis")
//        //    .call(d3.axisLeft(timeY));
//
//});
//
//function filter(d, startDate, endDate) {
//    // filter date range
//    if ( (startDate && d.pub_date < startDate) || (endDate && d.pub_date > endDate) ) { return false; }
//    // filter journals
//    if (!filteredJournals.hasOwnProperty("ALL")) {
//        if(!filteredJournals.hasOwnProperty(d.issn)) { return false; }
//    }
//    // filter subjects
//    //subject = "ALL";
//    //if (subject != "ALL") {
//    //    if(d.subject.indexOf(subject) == -1) { return false; }
//    //}
//    return true;
//}
//
//var selected;
//var clicked;
//var citing;
//var rLine;
//
//$(document).click( (event) => {
//    if(!($(event.target).closest("rect").length || $(event.target).closest(".point").length)) {
//        clearSelection()
//        clicked = null;
//        citing = null;
//        rLine = null;
//    }
//});
//
//function clearSelection() {
//    citeLine.remove();
//    decayLine.remove();
//    time.selectAll(".y-axis-right").remove();
//    time.selectAll(".axis-label-right").remove();
//    points.transition(200)
//        .style("r", 2)
//        .style("opacity", 0.4);
//    retractLines.transition(200)
//        .style("stroke-width", 1)
//        .style("opacity", 0);
//    overlayBars.attr("class", "bar overlay");
//    clickedBars = {};
//}
//
//function pointHover(d) {
//    if (clicked == null) {
//        if ($.isEmptyObject(clickedBars) || clickedBars.hasOwnProperty(d.lag)) {
//            selected = points.filter((o) => { return o.uid == d.uid; });
//            selected.transition(200)
//                .style("r", 5)
//                .style("opacity", 1);
//
//            tooltip.transition()
//                .duration(500)
//                .style("opacity", 0.9);
//            tooltip.html(tooltipText(d));
//
//    	    rLine = retractLines.filter((l) => { return l.uid == d.uid; })
//                .style("opacity", 0.6);
//        }
//    }
//}
//
//function pointLeave(d) {
//    if (clicked == null && selected != null) {
//        if ($.isEmptyObject(clickedBars)) {
//            selected.transition(200)
//                .style("r", 2)
//                .style("opacity", 0.4);
//        } else {
//            selected.transition(200)
//                .style("r", 5)
//                .style("opacity", 0.8);
//        }
//        selected = null;
//
//        rLine.transition(200)
//            .style("opacity", 0);
//        tooltip.transition(500)
//            .style("opacity", 0);
//    }
//}
//
//var selectedPoints;
//var clickedBars = {}
//function barClick(b) {
//    clickedBar = b;
//    if (clickedBars.hasOwnProperty(b.x0)) {
//        d3.select(this).classed("clicked", false);
//        delete clickedBars[b.x0];
//    } else {
//        d3.select(this).classed("clicked", true);
//        clickedBars[b.x0] = true;
//    }
//    selectedPoints = points.transition(200)
//        .style("r", 2)
//        .style("opacity", 0.2)
//        .filter( (d) => { return clickedBars.hasOwnProperty(d.lag); })
//        .style("r", 5)
//        .style("opacity", 0.8);
//}
//
//function cites(o, d) {
//    return cite_graph.hasOwnProperty(d) && cite_graph[d].indexOf(o) > -1;
//}
//
//function lineHover(d) {
//    n = null;
//    nodes.transition(200)
//        .style("opacity", o => {
//            if (o.uid == d.uid) { n = o; }
//            if (cites(o.uid, d.uid)) { return 0.8; }
//            else { return 0.1; }
//        })
//        .style("r", o => { return cites(o.uid, d.uid) ? 5 : 2; });
//
//    //rHoverLine = retractLines
//    //    .filter()
//    //    .transition(200)
//    //  .attr("y2", o => { return (o.uid == d.uid) ? 0 : height; })
//    //  .attr("stroke-width", o => { return (o.uid == d.uid) ? 2 : 0; })
//
//  const l = d3.select(this);
//    l.transition(200)
//        .attr("stroke-width", 2)
//        .style("opacity", 1)
//
//    tooltip.transition()
//        .duration(200)
//        .style("opacity", 0.9);
//    tooltip.html(tooltipText(n));
//}
//
//function lineLeave(d) {
//    nodes.transition(200)
//        .style("opacity", o => { return o.r_date ? 0.4 : 0.2; })
//        .style("r", o => { return o.r_date ? 5 : 2; });
//
//    rLine.transition(200)
//        .attr("y2", 0)
//        .style("opacity", 0.4)
//        .attr("stroke-width", 1)
//
//    tooltip.transition()
//        .duration(500)
//        .style("opacity", 0);
//}
//
//d3.selection.prototype.moveToFront = function() {
//  return this.each(function(){
//    this.parentNode.appendChild(this);
//  });
//};

}
