import { Component, OnInit } from "@angular/core";
import { TypeReading } from "src/app/consts/type-consts";
import { ReadingResult, Table } from "src/app/models/reading-model";
import { GameService } from "src/app/services/game.service";
import { ReadingService } from "src/app/services/reading.service";
import { TimerService } from "src/app/services/timer.service";

@Component({
  selector: "app-schubert",
  templateUrl: "./schubert.component.html",
  styleUrls: ["./schubert.component.css"],
})
export class SchubertComponent implements OnInit, GameService {
  numbers: number[] = [];
  tableRows: Table[] = [];
  currentNumber: number = 1;
  points: number = 0;
  size: number = 0;
  dateTime: string = ""
  hidden: boolean = true
  saved: boolean = false

  constructor(
    private readingService: ReadingService,
    private timerService: TimerService
  ) {}

  reset(): void {
    this.points = 0
    this.timerService.clearTimer()
    this.currentNumber = 1

  }
  startOrPause(): void {
    throw new Error("Method not implemented.");
  }

  calculatePoints(): void {
    let result:ReadingResult = {
      level: "",
      score: this.points,
      startTime: this.dateTime,
      time: 0,
      type: TypeReading.SCHULTZ
    }
  }
  
  goBack(): void {
    throw new Error("Method not implemented.");
  }

  ngOnInit(): void {
    this.timerService.getCurrentDate()
    this.readingService
      .getNumbersForSchubertTable("EASY")
      .subscribe((res: number[]) => {
        this.numbers = res;
        this.size = this.numbers.length;
        this.createTable();
      });
  }

  createTable() {
    var columnSize: number = Math.sqrt(this.size);
    for (var i = 0; i < this.size; i += columnSize) {
      var pom: number[] = this.numbers.slice(i, columnSize + i);
      var columnPom: Table = {
        column: pom,
      };
      this.tableRows.push(columnPom);
    }
  }

  checkPoints(num: number) {
    if (num == this.currentNumber) {
      this.points += 1;
      this.currentNumber += 1;
    } else {
      this.points -= 1;
    }
  }

  checkColumn(i: number): boolean {
    var numberOfColumns = Math.sqrt(this.size);
    if (numberOfColumns % i == 0) {
      return true;
    } else {
      return false;
    }
  }
}
