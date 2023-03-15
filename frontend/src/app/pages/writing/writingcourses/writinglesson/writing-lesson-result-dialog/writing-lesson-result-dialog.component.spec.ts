import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingLessonResultDialogComponent } from './writing-lesson-result-dialog.component';

describe('WritingLessonResultDialogComponent', () => {
  let component: WritingLessonResultDialogComponent;
  let fixture: ComponentFixture<WritingLessonResultDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingLessonResultDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingLessonResultDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
