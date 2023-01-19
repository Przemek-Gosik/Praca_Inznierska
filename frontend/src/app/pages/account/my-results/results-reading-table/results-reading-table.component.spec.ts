import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultsReadingTableComponent } from './results-reading-table.component';

describe('ResultsReadingTableComponent', () => {
  let component: ResultsReadingTableComponent;
  let fixture: ComponentFixture<ResultsReadingTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResultsReadingTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultsReadingTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
