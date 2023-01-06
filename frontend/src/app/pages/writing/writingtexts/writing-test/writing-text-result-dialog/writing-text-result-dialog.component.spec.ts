import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingTextResultDialogComponent } from './writing-text-result-dialog.component';

describe('WritingTextResultDialogComponent', () => {
  let component: WritingTextResultDialogComponent;
  let fixture: ComponentFixture<WritingTextResultDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingTextResultDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingTextResultDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
