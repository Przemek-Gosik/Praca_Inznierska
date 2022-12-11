import { Component, Input, OnInit } from '@angular/core';
import { Lesson, Module } from 'src/app/models/writing-model';

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
  isPanelExpanded: boolean = false;
  constructor() { }
  @Input() module?: Module

  ngOnInit(): void {
    if(this.module){
      this.name = this.module.name
      this.number = this.module.number
      this.lessons = this.module.lessons
    }
    
  }

}
