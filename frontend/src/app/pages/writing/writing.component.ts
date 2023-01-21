import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { WritingTextInstructionDialogComponent } from './writing-text-instruction-dialog/writing-text-instruction-dialog.component';

@Component({
  selector: 'app-writing',
  templateUrl: './writing.component.html',
  styleUrls: ['./writing.component.css']
})
export class WritingComponent implements OnInit {
  title: string = "Szybkie pisanie";
  course: string = "Przejdź do kursów";
  text: string = "Przejdź do pisania tekstów";
  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openInstructionForTexts():void{
    this.dialog.open(WritingTextInstructionDialogComponent,{
      width: '800px'
    });
  }
}
