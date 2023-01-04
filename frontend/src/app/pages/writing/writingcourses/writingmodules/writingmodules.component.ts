import { Component, Input, OnInit } from '@angular/core';
import { Lesson, Module } from 'src/app/models/writing-model';
import {Router} from '@angular/router';

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
  
  constructor(private router: Router) { }
  @Input() module?: Module

  ngOnInit(): void {
    if(this.module){
      this.name = this.module.name
      this.number = this.module.number
      this.lessons = this.module.lessons
    }
    
  }

  getLinkToLesson(id: number){
      let lessonId : number | undefined = this.lessons[id].idWritingLesson
      this.router.navigate(["/writing/course/lesson", {id :
        lessonId }
      ])
    }

}
