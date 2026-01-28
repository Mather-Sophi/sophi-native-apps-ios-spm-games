# PaywallKit Example App Structure

This example app demonstrates how to use the PaywallKit with configurable User Dimensions for testing.

## File Structure

```
src/
├── App.tsx                                    # Main application component
├── styles.ts                                  # All StyleSheet definitions
├── components/
│   ├── InputComponents.tsx                    # Reusable input components (SliderInput, TextInputField, RadioButtonGroup)
│   └── UserDimensionsDisplay.tsx             # Real-time display of current dimensions
└── repositories/
    └── MockRepositories.ts                    # Mock implementations of UserDimensionRepository and DeviceDimensionRepository
```

## Key Features

### 1. Dynamic User Dimensions
The app uses React state to manage all UserDimension values, which can be updated in real-time through the UI.

### 2. Synchronized Repository
When you click "Initialize Decider", fresh repository instances are created with the **current** state values, ensuring the dimensions match what's displayed.

### 3. Real-time Display
The `UserDimensionsDisplay` component shows the actual UserDimensions object that will be used by the PaywallDecider, ensuring what you see is what you get.

## How It Works

1. **State Management**: All dimension values are stored in React state
2. **useMemo Hook**: The `userDimensions` object is memoized and updates whenever any dimension value changes
3. **Fresh Repositories**: On initialization, new repository instances are created with current dimension values
4. **Synchronized Display**: The display component receives the same `userDimensions` object used by the repository

## Testing Workflow

1. Open the app
2. Click "Show User Dimensions Settings" to reveal configuration UI
3. Adjust values using:
   - **Numeric inputs** for counters (page views, visit counts, etc.)
   - **Text inputs** for strings (timezone, referrer URL, country code)
   - **Radio buttons** for enums (visitor type, referrer medium/source/channel)
4. View the real-time display showing current dimension values
5. Click "Initialize Decider" to create a PaywallDecider with these dimensions
6. Click "Make Decision" to test paywall logic with the configured dimensions

## Component Details

### SliderInput
Numeric input with min/max validation. Displays allowed range next to the input field.

### TextInputField
Simple text input for string values with optional placeholder.

### RadioButtonGroup
Radio button selector for enum values. Shows all options with visual indication of selection.

### UserDimensionsDisplay
Read-only display organized by category (Today's Metrics, 7-Day, 28-Day, Other) showing all current dimension values.
