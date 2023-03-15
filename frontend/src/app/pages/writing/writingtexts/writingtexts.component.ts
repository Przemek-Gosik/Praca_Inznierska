import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { LevelSelect } from "src/app/models/level-model";
import { LevelConsts } from "src/app/consts/level-consts";
import { WritingText } from "src/app/models/writing-model";
import { WritingService } from "src/app/services/writing.service";
import { Router } from "@angular/router";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { TextService } from "src/app/services/text.service";
@Component({
  selector: "app-writingtexts",
  templateUrl: "./writingtexts.component.html",
  styleUrls: ["./writingtexts.component.css"],
})
export class WritingtextsComponent implements OnInit {
  levels: LevelSelect[] = LevelConsts.LEVEL_SELECT1;
  chosenLevelName: string = "";
  chooseText: boolean = false;
  writingTexts: WritingText[] = [];
  dataSource = new MatTableDataSource<WritingText>();
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  displayedColumns: string[] = ["number", "title", "level", "action"];

  constructor(
    private httpWriting: WritingService,
    private router: Router,
    private textService: TextService
  ) {}

  ngOnInit(): void {}

  showTexts() {
    this.chooseText = true;
    if (this.chosenLevelName) {
      this.httpWriting
        .getAllTextsByLevel(this.chosenLevelName)
        .subscribe((res: WritingText[]) => {
          this.writingTexts = res;
          this.dataSource.data = this.writingTexts;
          this.dataSource.paginator = this.paginator!;
        });
    } else {
      this.httpWriting.getAllTexts().subscribe((res: WritingText[]) => {
        this.writingTexts = res;
        this.dataSource.data = this.writingTexts;
        this.dataSource.paginator = this.paginator!;
      });
    }
  }

  getLevelName(level: string): string {
    return this.textService.getLevelName(level)
  }

  startDrawedTest() {
    this.router.navigate([
      "/courses/writing/text/test",
      { id: 0, isDrawed: true , level: this.chosenLevelName },
    ]);
  }

  startTest(textId: number) {
    var isDrawed: boolean = false;
    this.router.navigate([
      "/courses/writing/text/test",
      { id: textId, isDrawed: isDrawed },
    ]);
  }
}
