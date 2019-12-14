package com.example.demo.controllers;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
  SolrQuery query = new SolrQuery();
  String urlString = "http://localhost:8983/solr/recipe";
  HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();

  @CrossOrigin
  @GetMapping("/")
  public String index() {
    // this attribute will be available in the view index.html as a thymeleaf variable
    //model.addAttribute("eventName", "FIFA 2018");
    // this just means render index.html from static/ area
    return "index";
  }

  @CrossOrigin
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public ResponseEntity<SolrDocumentList> returnSearchResults(@RequestParam String q, @RequestParam String s, @RequestParam Integer p) {
    int numItemsPerPage = 5;
    query.setStart((p - 1) * numItemsPerPage);
    query.setRows(numItemsPerPage);
    String field = "";
    int desc = -1;
    int asc = 1;
    int order = desc;
// execute the query on the server and get results
    query.set("defType", "edismax");
    query.set("bq", "product(DietaryScore,10)");
    //query.setRows(100);
    solr.setParser(new XMLResponseParser());
    if (q.equals("")) {
      return null;
    }

    if (s.equals("") || s.equals("dietary_steps")) {

    } else if (s.equals("protein")) {
      field = "ProteinsDV";
      // query.setSort("ProteinsDV", SolrQuery.ORDER.desc);
      query.set("bq", "product(ProteinsDV,10)");
      order = desc;
    } else if (s.equals("steps")) {
      field = "NumberOfSteps";
      // query.setSort("NumberOfSteps", SolrQuery.ORDER.asc);
      query.set("bq", "product(NumberOfSteps,0.01)");
      order = asc;
    } else if (s.equals("time")) {
      field = "TimeToMake";
      // query.setSort("TimeToMake", SolrQuery.ORDER.asc);
      query.set("bq", "product(TimeToMake,0.01)");
      order = asc;
    } else {
      //query.setSort("DietaryScore", SolrQuery.ORDER.desc);
      query.set("bq", "product(DietaryScore,10)");
      order = desc;
    }
    q = q.replace("+", " ");
    query.set("qf", "AllText^1 Name^10 RecipeTags^10 Steps^1 Ingredients^10");
    //query.set("q", "(Name:" + "(" + q + "*)) OR (Ingredients:(" + q + "*))");
    query.set("q", "AllText:(" + q + "*) OR Name:(\"" + q + "\")" + " OR Ingredients:(" + q + "*)");
    System.out.println(query.getQuery());
    //query.setQuery();

    try {
      QueryResponse response = solr.query(query);
      SolrDocumentList docList = response.getResults();
      System.out.println(docList.getNumFound());
      /*for (SolrDocument doc : docList) {

       recipeNames.add(doc.getFieldValue("Name").toString());


      }*/
      System.out.println(docList.get(0).getFieldValue("Name"));
      //return new ResponseEntity<>(docList, HttpStatus.OK);
      return new ResponseEntity<>(rerank(docList, field, order), HttpStatus.OK);
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //m.addAttribute("appName", "Search results");
    LinkedList l = new LinkedList<String>();
    return null;
  }


  SolrDocumentList rerank(SolrDocumentList l, String field, int order) {
    System.out.println("Inside reranking with field " + field);
    if (field.equals("")) {
      return l;
    }
    HashMap<SolrDocument, Double> map = new HashMap<>();
    for (SolrDocument doc : l) {
      System.out.println(doc.getFieldValue(field));
      map.put(doc, Double.parseDouble(String.valueOf(doc.getFieldValue(field))));
    }
    HashMap<SolrDocument, Double> sortedMap = new HashMap<>();
    if (order == 1) {
      sortedMap = sortByValueAsc(map);
    } else {
      sortedMap = sortByValueDsc(map);
    }
    SolrDocumentList newList = new SolrDocumentList();

    newList.addAll(sortedMap.keySet());
    System.out.println("After reranking");
    for (SolrDocument doc : newList) {
      System.out.println(doc.getFieldValue(field));
      map.put(doc, Double.parseDouble(String.valueOf(doc.getFieldValue(field))));
    }
    return newList;
  }


  // function to sort hashmap by values
  private static HashMap<SolrDocument, Double> sortByValueAsc(HashMap<SolrDocument, Double> hm) {
    // Create a list from elements of HashMap
    List<Map.Entry<SolrDocument, Double>> list =
            new LinkedList<Map.Entry<SolrDocument, Double>>(hm.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<SolrDocument, Double>>() {
      public int compare(Map.Entry<SolrDocument, Double> o1,
                         Map.Entry<SolrDocument, Double> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // put data from sorted list to hashmap
    HashMap<SolrDocument, Double> temp = new LinkedHashMap<SolrDocument, Double>();
    for (Map.Entry<SolrDocument, Double> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }

  private static HashMap<SolrDocument, Double> sortByValueDsc(HashMap<SolrDocument, Double> hm) {
    // Create a list from elements of HashMap
    List<Map.Entry<SolrDocument, Double>> list =
            new LinkedList<Map.Entry<SolrDocument, Double>>(hm.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<SolrDocument, Double>>() {
      public int compare(Map.Entry<SolrDocument, Double> o1,
                         Map.Entry<SolrDocument, Double> o2) {
        return (o1.getValue()).compareTo(o2.getValue()) * -1;
      }
    });

    // put data from sorted list to hashmap
    HashMap<SolrDocument, Double> temp = new LinkedHashMap<SolrDocument, Double>();
    for (Map.Entry<SolrDocument, Double> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }

}