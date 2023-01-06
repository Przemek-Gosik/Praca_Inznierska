import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Lesson, WritingLessonResult } from 'src/app/models/writing-model';
import { LoginService } from 'src/app/services/login.service';
import { WritingService } from 'src/app/services/writing.service';

@Component({
  selector: 'app-writing-lesson-result-dialog',
  templateUrl: './writing-lesson-result-dialog.component.html',
  styleUrls: ['./writing-lesson-result-dialog.component.css']
})
export class WritingLessonResultDialogComponent implements OnInit {
  typedLetters: number = 0;
  LESSON_MAX_POINTS: number = 50;
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
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private writingService: WritingService,private loginService:LoginService) { }
  
  ngOnInit(): void {
    this.score = this.data.score
    this.idLesson = this.data.idLesson
    this.typedLetters = this.data.numberOfTypedLetters
    this.time = this.data.time
    this.dateTime = this.data.date
    console.log(this.data.idResult)
    if(this.data.idResult){
      this.writingService.getLessonResultById(this.data.idResult).subscribe((res:WritingLessonResult)=>{
        this.lastResult = res
        console.log(this.lastResult)
      })
    }
  
  }

  loggedIn():boolean{
    return this.loginService.loggedInUser()
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
      score: this.score/this.LESSON_MAX_POINTS,
      time: this.time,
      numberOfTypedLetters: this.typedLetters
    }
    this.writingService.saveLessonResult(result).subscribe((res:any)=>{
      console.log(res)
    })
  }

    updateResult(){
      this.lastResult.score = this.score/this.LESSON_MAX_POINTS
      this.lastResult.startTime = this.dateTime
      this.lastResult.numberOfTypedLetters = this.typedLetters
      this.lastResult.time = this.time
      this.writingService.updateLessonResult(this.lastResult).subscribe((res:any)=>{
        console.log(res)
      })
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

