import { Stack } from "expo-router";
import { useEffect, useRef } from "react";
import { AppState, AppStateStatus } from "react-native";
import { initializePaywallKit } from "./paywall/decider";

export default function RootLayout() {
  const appState = useRef(AppState.currentState);

  useEffect(() => {
    // Initialize on app startup
    try {
      initializePaywallKit();
    } catch (error) {
      console.error('Failed to initialize PaywallKit:', error);
    }

    // Handle app state changes (resume)
    const subscription = AppState.addEventListener('change', (nextAppState: AppStateStatus) => {
      if (appState.current.match(/inactive|background/) && nextAppState === 'active') {
        // App has come to the foreground
        try {
          initializePaywallKit();
        } catch (error) {
          console.error('Failed to re-initialize PaywallKit:', error);
        }
      }
      appState.current = nextAppState;
    });

    return () => {
      subscription.remove();
    };
  }, []);

  return <Stack />;
}
