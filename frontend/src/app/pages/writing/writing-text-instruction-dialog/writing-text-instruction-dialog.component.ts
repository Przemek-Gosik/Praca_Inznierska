import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-writing-text-instruction-dialog',
  templateUrl: './writing-text-instruction-dialog.component.html',
  styleUrls: ['./writing-text-instruction-dialog.component.css']
})
export class WritingTextInstructionDialogComponent implements OnInit {

  constructor(public themeService: ThemeService,private router: Router) { }

  ngOnInit(): void {
  } 

  goBack():void{
    this.router.navigate(["courses/writing"])
  }

  startTask():void{
    this.router.navigate(['courses/writing/text'])
  }
}
