import { Component, Input, OnInit } from '@angular/core';
import { Lesson, Module } from 'src/app/models/writing-model';
import {Router} from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-writingmodules',
  templateUrl: './writingmodules.component.html',
  styleUrls: ['./writingmodules.component.css']
})
export class WritingmodulesComponent implements OnInit {
  name: String="nazwa modułu"
  number: number = 0
  lessonName: String = "Lekcja nr"
  lessons: Lesson[]=[]
  
  constructor(private router: Router,private loginService: LoginService) { }

  @Input() module?: Module

  ngOnInit(): void {
    if(this.module){
      this.name = this.module.name
      this.number = this.module.number
      this.lessons = this.module.lessons
    }
    
  }

  loggedInUser(){
    return this.loginService.loggedInUser()
  }

  calculateLastScore(score:number):number{
    return score*100
  }

  getLinkToLesson(id: number){
      let lessonId : number | undefined = this.lessons[id].idWritingLesson
      this.router.navigate(["courses/writing/course/lesson", {id :
        lessonId,
        lastResultId: this.lessons[id].idWritingLessonResult }
      ])
    }

}
