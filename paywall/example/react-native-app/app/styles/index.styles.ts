import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    marginTop: 40,
    marginBottom: 20,
    alignItems: 'center',
    paddingHorizontal: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  loadingText: {
    marginTop: 10,
    fontSize: 16,
    color: '#666',
  },
  card: {
    backgroundColor: '#fff',
    paddingVertical: 16,
  },
  divider: {
    height: 1,
    backgroundColor: '#e0e0e0',
  },
  cardContent: {
    paddingHorizontal: 16,
  },
  articleImage: {
    width: '100%',
    height: 200,
    backgroundColor: '#e0e0e0',
    marginBottom: 12,
  },
  headline: {
    fontSize: 18,
    fontWeight: '600',
    color: '#333',
    marginBottom: 8,
    lineHeight: 24,
  },
  plainText: {
    fontSize: 14,
    color: '#666',
    lineHeight: 20,
  },
  readMore: {
    fontSize: 14,
    color: '#4a90e2',
    fontWeight: '600',
    marginTop: 8,
  },
  decisionInfo: {
    marginTop: 12,
    paddingTop: 12,
    borderTopWidth: 1,
    borderTopColor: '#f0f0f0',
  },
  decisionLabel: {
    fontSize: 12,
    fontWeight: '600',
    color: '#4a90e2',
    marginBottom: 4,
  },
  decisionText: {
    fontSize: 11,
    color: '#555',
    marginBottom: 2,
  },
  errorText: {
    fontSize: 11,
    color: '#d32f2f',
  },
});
