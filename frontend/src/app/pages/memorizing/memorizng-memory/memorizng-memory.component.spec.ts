import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemorizngMemoryComponent } from './memorizng-memory.component';

describe('MemorizngMemoryComponent', () => {
  let component: MemorizngMemoryComponent;
  let fixture: ComponentFixture<MemorizngMemoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemorizngMemoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemorizngMemoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
