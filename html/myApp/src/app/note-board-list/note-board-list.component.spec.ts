import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoteBoardListComponent } from './note-board-list.component';

describe('NoteBoardListComponent', () => {
  let component: NoteBoardListComponent;
  let fixture: ComponentFixture<NoteBoardListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NoteBoardListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NoteBoardListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
