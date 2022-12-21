import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Lesson, WritingResult } from 'src/app/models/writing-model';
import { TokenService } from 'src/app/services/token.service';
import { WritingService } from 'src/app/services/writing.service';

@Component({
  selector: 'app-writing-result-dialog',
  templateUrl: './writing-result-dialog.component.html',
  styleUrls: ['./writing-result-dialog.component.css']
})
export class WritingResultDialogComponent implements OnInit {
  lesson! : Lesson;
  typedLetters!: number;
  time!: number;
  dateTime!: string
  result: WritingResult = {
    idFastWritingCourse: 0,
    idFastWritingLesson: 0,
    numberOfAttempts: 0, 
    startTime: "",
    score: 0,
    typedLetters: 0
  }
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private httpWriting: WritingService) { }
  
  ngOnInit(): void {
    this.lesson = this.data.lesson
    this.typedLetters = this.data.typedLetters
    this.time = this.data.time
    this.dateTime = this.data.date
    if(this.lesson.idFastWritingCourse){
      this.httpWriting.getCourseById(this.lesson.idFastWritingCourse).subscribe((res:WritingResult)=>{
        this.result = res
      })
    }
  }
  
  calculatePrecision():string{
    var precision : number = 0;
    if(this.lesson.score){
      precision = (this.lesson.score*1.0/this.typedLetters)*100
    }
    return precision.toFixed(2)+'%'
  }

  calculateSpeed():string{
    var speed : number = this.typedLetters*1.0/ this.time
    return (speed*60).toFixed(2)
  }

  saveResult(){
    this.result = {
      idFastWritingLesson: this.lesson.idFastWritingLesson!,
      startTime: this.dateTime,
      score: this.lesson.score!,
      typedLetters: this.typedLetters
    }
    console.log(this.result)
    }
  }

