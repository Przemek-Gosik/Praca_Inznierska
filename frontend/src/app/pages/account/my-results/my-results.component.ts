import { Component, OnInit } from "@angular/core";
import { MemorizngResult } from "src/app/models/memorizing-model";
import { ReadingResult } from "src/app/models/reading-model";
import { WritingTextResult } from "src/app/models/writing-model";
import { MemorizingService } from "src/app/services/memorizing.service";
import { ReadingService } from "src/app/services/reading.service";
import { WritingService } from "src/app/services/writing.service";
import { MatTableDataSource} from '@angular/material/table'

@Component({
  selector: "app-my-results",
  templateUrl: "./my-results.component.html",
  styleUrls: ["./my-results.component.css"],
})
export class MyResultsComponent implements OnInit {
  title: string = "Moje wyniki";
  reading: string = "Szybkie czytanie";
  memorizing: string = "Zapamiętywanie";
  writing: string = "Szybkie pisanie";
  memorizingResults: MemorizngResult[] =[];
  readingResults: ReadingResult[] = [];
  writingResults: WritingTextResult[] =[];
  memorzingDataSource = new MatTableDataSource<MemorizngResult>()

  constructor(
    private memorizingService: MemorizingService,
    private readingService: ReadingService,
    private writingService: WritingService
  ) {}

  ngOnInit(): void {
    this.getMemorzingResults()
    this.getReadingResults()
    this.getWritingResults()
  }

  getMemorzingResults():void{
    this.memorizingService.getAllUserResults().subscribe((res:MemorizngResult[])=>{
      this.memorizingResults = res
    })
  }

  getReadingResults():void{
    this.readingService.getAllResults().subscribe((res:ReadingResult[])=>{
      this.readingResults = res
    })
  }

  getWritingResults():void{
    this.writingService.getAllTextResults().subscribe((res:WritingTextResult[])=>{
      this.writingResults = res
      console.log(res)
    })
  }
}
