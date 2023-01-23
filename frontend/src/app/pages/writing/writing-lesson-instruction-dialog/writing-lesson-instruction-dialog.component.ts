import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ThemeService } from 'src/app/services/theme.service';
import { IntructionTexts, IntructionTitles } from 'src/app/consts/instruction-consts';

@Component({
  selector: 'app-writing-lesson-instruction-dialog',
  templateUrl: './writing-lesson-instruction-dialog.component.html',
  styleUrls: ['./writing-lesson-instruction-dialog.component.css']
})
export class WritingLessonInstructionDialogComponent implements OnInit {

  constructor(public themeService: ThemeService,private router: Router) { }
  TITLE: string = IntructionTitles.WRITING_LESSON;
  TEXT_LINE1: string = IntructionTexts.WRITING_LESSON_LESSON1;
  TEXT_LINE2: string = IntructionTexts.WRITING_LESSON_LESSON2;
  TEXT_LINE3: string = IntructionTexts.WRITING_LESSON_LESSON3;
  TEXT_LINE4: string = IntructionTexts.WRITING_LESSON_LESSON4;
  ngOnInit(): void {
  }

  startTask():void{
    this.router.navigate(['/courses/writing/course'])
  }

  goBack():void{
    this.router.navigate(['courses/writing'])
  }

}
