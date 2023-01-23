import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IntructionTexts, IntructionTitles } from 'src/app/consts/instruction-consts';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-writing-text-instruction-dialog',
  templateUrl: './writing-text-instruction-dialog.component.html',
  styleUrls: ['./writing-text-instruction-dialog.component.css']
})
export class WritingTextInstructionDialogComponent implements OnInit {

  constructor(public themeService: ThemeService,private router: Router) { }
  TITLE: string = IntructionTitles.WRITING_TEXT;
  TEXT_LINE1:string= IntructionTexts.WRITING_TEXT1;
  TEXT_LINE2:string= IntructionTexts.WRITING_TEXT2;
  TEXT_LINE3:string= IntructionTexts.WRITING_TEXT3;
  TEXT_LINE4:string= IntructionTexts.WRITING_TEXT4;
  ngOnInit(): void {
  } 

  goBack():void{
    this.router.navigate(["courses/writing"])
  }

  startTask():void{
    this.router.navigate(['courses/writing/text'])
  }
}
