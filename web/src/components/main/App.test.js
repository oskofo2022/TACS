import { render, screen } from '@testing-library/react';
import App from './App';

test('renders bare bones', () => {
  render(<App />);
  const linkElement = screen.getByText(/bare bones/i);
  expect(linkElement).toBeInTheDocument();
});
