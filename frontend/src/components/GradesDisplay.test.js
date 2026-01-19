import React from 'react';
import { render, screen } from '@testing-library/react';
import GradesDisplay from './GradesDisplay';

// Mock der Services
jest.mock('../services/criterionProgressService', () => ({
  criterionProgressService: {
    getByPersonId: jest.fn(),
  },
}));

jest.mock('../services/criterionService', () => ({
  criterionService: {
    getAll: jest.fn(),
  },
}));

describe('GradesDisplay Component', () => {
  
  describe('Rendering ohne personId', () => {
    test('sollte Info-Message zeigen wenn keine personId', () => {
      render(<GradesDisplay />);
      
      expect(screen.getByText(/bitte wählen sie eine person/i)).toBeInTheDocument();
    });
    
    test('sollte Container-Div rendern', () => {
      const { container } = render(<GradesDisplay />);
      
      expect(container.querySelector('.grades-display-container')).toBeInTheDocument();
    });
  });
});
