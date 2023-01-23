import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingLessonInstructionDialogComponent } from './writing-lesson-instruction-dialog.component';

describe('WritingLessonInstructionDialogComponent', () => {
  let component: WritingLessonInstructionDialogComponent;
  let fixture: ComponentFixture<WritingLessonInstructionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingLessonInstructionDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingLessonInstructionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
