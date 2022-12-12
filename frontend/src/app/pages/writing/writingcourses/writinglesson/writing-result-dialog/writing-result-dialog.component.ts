import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Result } from 'src/app/models/writing-model';

@Component({
  selector: 'app-writing-result-dialog',
  templateUrl: './writing-result-dialog.component.html',
  styleUrls: ['./writing-result-dialog.component.css']
})
export class WritingResultDialogComponent implements OnInit {

  constructor() { }
  
  ngOnInit(): void {
  }

}
