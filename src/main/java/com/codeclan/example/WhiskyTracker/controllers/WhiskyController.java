package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WhiskyController {

  @Autowired
  WhiskyRepository whiskyRepository;

  @GetMapping(value = "/whiskies")
  public ResponseEntity<List<Whisky>> getAllWhiskies(

    @RequestParam(name = "year", required = false) Integer year,
    @RequestParam(name = "age", required = false) Integer age,
    @RequestParam(name = "distillery", required = false) String distilleryName,
    @RequestParam(name = "region", required = false) String region,
    @RequestParam(name = "namecontaining", required = false) String snippet,
    @RequestParam(name = "namestartingwith", required = false) String startingSnippet
  )
  {

    if(startingSnippet != null){
      return  new ResponseEntity<>(whiskyRepository.findByNameStartingWith(startingSnippet), HttpStatus.OK);
    }
    if(snippet != null){
      return  new ResponseEntity<>(whiskyRepository.findByNameContaining(snippet), HttpStatus.OK);
    }
    if(region != null){
      return  new ResponseEntity<>(whiskyRepository.findByDistilleryRegion(region), HttpStatus.OK);
    }
    if (distilleryName != null && age != null){
      return  new ResponseEntity<>(whiskyRepository.findByAgeAndDistilleryName(age, distilleryName), HttpStatus.OK);
    }
    if (year != null){
      return  new ResponseEntity<>(whiskyRepository.findByYear(year), HttpStatus.OK);
    }
    List<Whisky> foundWhiskies = whiskyRepository.findAll();
    return new ResponseEntity<List<Whisky>>(foundWhiskies, HttpStatus.OK);
  }

  @GetMapping(value = "/whiskies/{id}")
  public ResponseEntity getWhisky(@PathVariable Long id){
    return new ResponseEntity<>(whiskyRepository.findById(id), HttpStatus.OK);
  }

  @PostMapping(value = "/whiskies")
  public ResponseEntity<Whisky> postWhisky(@RequestBody Whisky whisky){
    whiskyRepository.save(whisky);
    return new ResponseEntity<>(whisky, HttpStatus.CREATED);
  }

  @PutMapping(value = "/whiskies/{id}")
  public ResponseEntity<Whisky> putWhisky(@RequestBody Whisky whisky, @PathVariable Long id){
    whiskyRepository.save(whisky);
    return new ResponseEntity<>(whisky, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/whiskies/{id}")
  public ResponseEntity<List<Whisky>> deleteWhisky(@PathVariable Long id){
    whiskyRepository.deleteById(id);
    return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
  }
}
