import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Lesson, WritingLessonResult } from 'src/app/models/writing-model';
import { WritingService } from 'src/app/services/writing.service';

@Component({
  selector: 'app-writing-lesson-result-dialog',
  templateUrl: './writing-lesson-result-dialog.component.html',
  styleUrls: ['./writing-lesson-result-dialog.component.css']
})
export class WritingLessonResultDialogComponent implements OnInit {
  typedLetters: number = 0;
  time: number = 0;
  dateTime: string = "";
  score : number = 0;
  idLesson : number = 0;
  lastResult: WritingLessonResult = {
    idWritingLessonResult: 0,
    idWritingLesson: 0,
    numberOfAttempts: 0,
    startTime: "",
    score: 0,
    numberOfTypedLetters: 0,
    time: 0
  }
  saved: boolean = false
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private httpWriting: WritingService) { }
  
  ngOnInit(): void {
    this.score = this.data.score
    this.idLesson = this.data.idLesson
    this.typedLetters = this.data.numberOfTypedLetters
    this.time = this.data.time
    this.dateTime = this.data.date
    if(this.data.idResult){
      this.httpWriting.getLessonResultById(this.data.idResult).subscribe((res:WritingLessonResult)=>{
        this.lastResult = res
      })
    }
  
  }
  
  calculatePrecision():string{
    var precision : number = 0;
    precision = (this.score*1.0/this.typedLetters)*100
    return precision.toFixed(2)+'%'
  }

  calculateSpeed():string{
    var speed : number = this.typedLetters*1.0/ this.time
    return (speed*60).toFixed(2)
  }

  saveResult(){
    var result: WritingLessonResult = {
      idWritingLesson: this.idLesson,
      startTime: this.dateTime,
      score: this.score,
      time: 0,
      numberOfTypedLetters: this.typedLetters
    }
    this.httpWriting.saveLessonResult(result)
    }

    updateResult(){
      this.lastResult.score = this.score
      this.lastResult.startTime = this.dateTime
      this.lastResult.numberOfTypedLetters = this.typedLetters
      this.lastResult.time = this.time
      this.httpWriting.updateLessonResult(this.lastResult)

    }

    saveOrUpdate(){
      if(this.lastResult.numberOfAttempts! > 0 ){
        this.updateResult()
      }else{
        this.saveResult()
      }
      this.saved = true
    }
  }

